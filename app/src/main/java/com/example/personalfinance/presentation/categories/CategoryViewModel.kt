package com.example.personalfinance.presentation.categories

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.home.entity.Record
import com.example.personalfinance.domain.category.usecases.AddCategoryUseCase
import com.example.personalfinance.domain.category.usecases.GetCategoriesUseCase
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    useCaseExecutor: UseCaseExecutor
):BaseViewModel(useCaseExecutor) {

    private val _incomeCategoryList = MutableStateFlow(mutableListOf<Category>())
    val incomeCategoryList: StateFlow<MutableList<Category>> = _incomeCategoryList

    private val _expanseCategoryList = MutableStateFlow(mutableListOf<Category>())
    val expanseCategoryList: StateFlow<MutableList<Category>> = _expanseCategoryList

    init {
        useCaseExecutor.execute(getCategoriesUseCase,
            Unit,
            ::pot
        )
    }

    fun pot(records: Flow<List<Category>>) {
        viewModelScope.launch {
            records.collect {
                _incomeCategoryList.value = it.toMutableList()
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