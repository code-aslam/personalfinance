package com.example.personalfinance.presentation.home

import android.util.Log
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.home.models.Record
import com.example.personalfinance.domain.home.usecases.FetchRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRecordsUseCase: FetchRecordsUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor){

    fun test(){
        Log.e("aslam", "hshkfsfhfhkjshfjshkh")
        useCaseExecutor.execute(fetchRecordsUseCase,"", ::pot)
    }

    fun pot(records : List<Record>){
        Log.e("aslam", records[0].amount.toString())
    }
}