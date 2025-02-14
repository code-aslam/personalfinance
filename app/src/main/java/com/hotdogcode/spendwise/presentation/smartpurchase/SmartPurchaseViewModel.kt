package com.hotdogcode.spendwise.presentation.smartpurchase

import android.util.Log
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import com.hotdogcode.spendwise_ml.SpendWisePurchaseAdviser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmartPurchaseViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val spendWisePurchaseAdviser: SpendWisePurchaseAdviser
) : BaseViewModel(useCaseExecutor){
    init {
        Log.e("Aslam", spendWisePurchaseAdviser.analyzePurchase().isGoodPurchase.toString())
    }

    /**
     * features = ["account_balance", "credit_limit", "monthly_income", "monthly_expense",
     *             "debt_to_income_ratio", "category_spending_avg", "day_of_week", "hour_of_day", "amount"]
     */
}