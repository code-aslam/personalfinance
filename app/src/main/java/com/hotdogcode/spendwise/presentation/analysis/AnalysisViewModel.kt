package com.hotdogcode.spendwise.presentation.analysis

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.common.RANDOM_COLOR_LIST
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordForCategoryUseCase
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordsUseCase
import com.hotdogcode.spendwise.presentation.analysis.components.Bar
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AnalysisViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val getRecordsUseCase: GetRecordsUseCase,

    ) : BaseViewModel(useCaseExecutor){



    private val  map = mutableMapOf<Long,List<RecordWithCategoryAndAccount>>()

    private val _barList = MutableStateFlow(listOf<Bar>())
    var barList = _barList.asStateFlow()

    private val _showDetails = MutableStateFlow(false)
    var showDetails = _showDetails.asStateFlow()

    init {
        getRecordsForCategory()
    }

    @SuppressLint("DefaultLocale")
    private fun getRecordsForCategory(){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordsUseCase,
                Unit
            ){
                    records ->
                viewModelScope.launch {
                    records.collect { list ->

                        for(record in list) {
                            if (record.transactionType == TransactionType.EXPENSE) {
                                if (!map.containsKey(record.categoryId)) {
                                    map[record.categoryId] = listOf(record)
                                } else {
                                    map[record.categoryId] = map[record.categoryId]!!.plus(record)
                                }
                            }
                        }
                        val list = mutableListOf<Bar>()
                        var totalSpending = 0.0
                        for((id,categories) in map){
                            totalSpending += categories.sumOf { it.amount }
                        }
                        for((id,categories) in map){
                            val bar = Bar(categoryId = id)
                            bar.color = categories[0].categoryIcon.color
                            bar.categoryIcon = categories[0].categoryIcon
                            bar.categoryName = categories[0].categoryTitle
                            bar.spending = categories.sumOf { it.amount }
                            bar.percentage =   String.format("%.2f", ((bar.spending / totalSpending) * 100)).toDouble()
                            list.add(bar)
                        }
                        _barList.value = list

                    }
                }
            }
        }
    }

    fun showDetailsAction() {
        _showDetails.value = true
    }

    fun hideDetailsAction() {
        _showDetails.value = false
    }
}