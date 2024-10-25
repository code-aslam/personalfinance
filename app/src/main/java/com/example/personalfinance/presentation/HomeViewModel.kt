package com.example.personalfinance.presentation

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.home.usecases.FetchRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.personalfinance.data.home.entity.Record
import com.example.personalfinance.presentation.categories.model.Category
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val fetchRecordsUseCase: FetchRecordsUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {

    private val _incomeCategoryList = MutableStateFlow(mutableListOf<Category>())
    val incomeCategoryList: StateFlow<MutableList<Category>> = _incomeCategoryList

    private val _expanseCategoryList = MutableStateFlow(mutableListOf<Category>())
    val expanseCategoryList: StateFlow<MutableList<Category>> = _expanseCategoryList

    private val _recordList = MutableStateFlow(emptyList<Record>())
    val recordList: StateFlow<List<Record>> = _recordList

    init {
        addIncomeCategory(
            Category(
                title = "Income",
                icon = R.drawable.salary
            )
        )

        addExpanseCategory(
            Category(
                title = "Expanse",
                icon = R.drawable.salary
            )
        )
    }

    fun test() {
        useCaseExecutor.execute(fetchRecordsUseCase, "", ::pot)
    }

    fun pot(records: Flow<List<Record>>) {
        viewModelScope.launch {
            records.collect {
                _recordList.value = it
            }
        }
    }

    fun addIncomeCategory(category: Category) {
        viewModelScope.launch {
            category.type = CategoryType.INCOME
            _incomeCategoryList.value.add(category)
        }
    }

    fun removeIncomeCategory(category: Category) {
        viewModelScope.launch {
            _incomeCategoryList.value.remove(category)
        }
    }

    fun addExpanseCategory(category: Category) {
        viewModelScope.launch {
            category.type = CategoryType.EXPENSE
            _expanseCategoryList.value.add(category)
        }
    }

    fun removeExpanseCategory(category: Category) {
        viewModelScope.launch {
            _expanseCategoryList.value.remove(category)
        }
    }


}