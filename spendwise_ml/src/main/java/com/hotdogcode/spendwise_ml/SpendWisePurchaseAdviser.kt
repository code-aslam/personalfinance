package com.hotdogcode.spendwise_ml

import kotlinx.coroutines.flow.Flow
import org.tensorflow.lite.Interpreter


class SpendWisePurchaseAdviser(private val model: Interpreter) {

    fun analyzePurchase(): PurchaseDecision {
//        val inputData = floatArrayOf(
//            transaction.amount.toFloat(),
//            // Add other relevant features for prediction
//        )
//
//        val output = arrayOf(FloatArray(1))
//        model.run(inputData, output)

        val confidence = 0.4f
        return PurchaseDecision(
            isGoodPurchase = confidence > 0.5,
            confidence = confidence,
            reason = if (confidence > 0.5) "This purchase is within your budget" else "This purchase might impact your savings"
        )
    }
}

data class PurchaseTransaction(
    var accountBalance : Int,
    var creditLimit : Int,
    var monthlyIncome : Int,
    var monthlyExpense : Int,
    var debtToIncomeRatio : Float,
    var categorySpendingAvg : Int,
    var dayOfWeek : Int,
    var hourOfDay : Double,
    var amount : Int
)

data class PurchaseDecision(
    val isGoodPurchase: Boolean,
    val confidence: Float,
    val reason: String
)
