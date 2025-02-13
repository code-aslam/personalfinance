package com.hotdogcode.spendwise.presentation.smartpurchase

import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SmartPurchaseViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor){

}