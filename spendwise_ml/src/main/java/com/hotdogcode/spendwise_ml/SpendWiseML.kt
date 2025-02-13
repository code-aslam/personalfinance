package com.hotdogcode.spendwise_ml

import android.content.Context
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SpendWiseML private constructor(context : Context, mlTask: MLTask){
    private var interpreter : Interpreter? = null

    init {
        loadModelFile(context,mlTask)
    }

    companion object {
        @Volatile
        private var instances: MutableMap<String, SpendWiseML> = mutableMapOf()

        fun getInstance(context: Context, mlTask: MLTask): SpendWiseML {
            return instances.getOrPut(mlTask.name) {
                SpendWiseML(context, mlTask)
            }
        }
    }

    private fun loadModelFile(context: Context, mlTask: MLTask): MappedByteBuffer {
        var modelFileName = "spendwise_purchase_adviosr.tflite"
        if (mlTask == MLTask.SMART_PURCHASE_ADVISER) {
            modelFileName = "spendwise_purchase_adviosr.tflite"
        }
        val fileDescriptor = context.assets.openFd(modelFileName)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    fun getPurchaseAdvise() : PredictionResult {
//        val input = floatArrayOf(
//            transaction.accountBalance,
//            transaction.creditLimit,
//            transaction.monthlyIncome,
//            transaction.monthlyExpense,
//            transaction.debtToIncomeRatio,
//            transaction.categorySpendingAvg,
//            transaction.dayOfWeek.toFloat(),
//            transaction.hourOfDay.toFloat(),
//            transaction.amount
//        )
//
//        val output = Array(1) { FloatArray(1) }
//        interpreter?.run(input, output)
//
//        return PredictionResult(output[0][0])
        return PredictionResult(0f)
    }

}
//data class TransactionData(
//    val accountBalance: Float,
//    val creditLimit: Float,
//    val monthlyIncome: Float,
//    val monthlyExpense: Float,
//    val debtToIncomeRatio: Float,
//    val categorySpendingAvg: Float,
//    val dayOfWeek: Int,
//    val hourOfDay: Int,
//    val amount: Float
//)
data class PredictionResult(val score: Float) {
    fun isGoodPurchase(): Boolean = score > 0.5f
}