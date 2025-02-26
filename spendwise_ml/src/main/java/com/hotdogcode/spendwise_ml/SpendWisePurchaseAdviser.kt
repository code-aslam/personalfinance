package com.hotdogcode.spendwise_ml

import android.util.Log
import kotlinx.coroutines.flow.Flow
import org.tensorflow.lite.Interpreter


class SpendWisePurchaseAdviser(private val model: Interpreter) {

    fun analyzePurchase(transaction: PurchaseTransaction): PurchaseSuggestion {
        val inputData = floatArrayOf(
            transaction.accountBalance.toFloat(),
            transaction.creditLimit.toFloat(),
            transaction.monthlyIncome.toFloat(),
            transaction.monthlyExpense.toFloat(),
            transaction.debtToIncomeRatio.toFloat(),
            transaction.categorySpendingAvg.toFloat(),
            transaction.dayOfWeek.toFloat(),
            transaction.hourOfDay.toFloat(),
            transaction.amount.toFloat(),
            1f,
            1f
        )

        val output = Array(1) { FloatArray(1) }
        model.run(inputData, output)
        val outputIndex = 0 // Usually the first output tensor
        val outputTensor = model.getInputTensor(outputIndex)
        val outputShape = outputTensor.shape() // Returns an IntArray

        Log.d("aslam", "Output Shape: ${outputShape.joinToString()}")
        val confidence = output[0][0]
        return PurchaseSuggestion(
            isGoodPurchase = confidence > 0.5,
            confidence = confidence,
            reason = if (confidence > 0.5) "This purchase is within your budget" else "This purchase might impact your savings"
        )
    }
}

data class PurchaseTransaction(
    var accountBalance : Double = 0.0,
    var creditLimit : Double = 0.0,
    var monthlyIncome : Double = 0.0,
    var monthlyExpense : Double = 0.0,
    var debtToIncomeRatio : Double = 0.0,
    var categorySpendingAvg : Double = 0.0,
    var dayOfWeek : Int = 1,
    var hourOfDay : Double = 0.0,
    var amount : Int = 0
)

data class PurchaseSuggestion(
    val isGoodPurchase: Boolean,
    val confidence: Float,
    val reason: String
)
