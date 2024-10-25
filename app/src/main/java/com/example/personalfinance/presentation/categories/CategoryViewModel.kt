package com.example.personalfinance.presentation.categories

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.domain.category.usecases.AddCategoryUseCase
import com.example.personalfinance.domain.category.usecases.DeleteCategoryUseCase
import com.example.personalfinance.domain.category.usecases.GetCategoriesUseCase
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val addCategoryUseCase: AddCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {

    private val _incomeCategoryList = MutableStateFlow(mutableListOf<Category>())
    val incomeCategoryList = _incomeCategoryList.asStateFlow()

    private val _expanseCategoryList = MutableStateFlow(mutableListOf<Category>())
    val expanseCategoryList = _expanseCategoryList.asStateFlow()

    init {
        updateCategories()
    }

    private fun updateCategories(){
        useCaseExecutor.execute(
            getCategoriesUseCase,
            Unit,
            ::handleCategories
        )
    }

    private fun handleCategories(records: Flow<List<Category>>) {
        val incomeList = _incomeCategoryList.value
        val expenseList = _expanseCategoryList.value
        viewModelScope.launch {
            records.collect { list ->
                list.forEach {cat->
                    when(cat.type){
                        CategoryType.INCOME -> incomeList.add(cat)
                        else -> expenseList.add(cat)
                    }
                }
            }
            _incomeCategoryList.value = incomeList
            _expanseCategoryList.value = expenseList
        }
    }


    private fun addCategory(category: Category) {
        val incomeList = _incomeCategoryList.value.toMutableList()
        val expenseList = _expanseCategoryList.value.toMutableList()
        useCaseExecutor.execute(addCategoryUseCase, category){
            category.id = it
            if(category.type == CategoryType.INCOME)
                incomeList.add(category)
            else
                expenseList.add(category)
            _incomeCategoryList.value = incomeList
            _expanseCategoryList.value = expenseList
        }
    }

    private fun removeCategory(category: Category) {
        val incomeList = _incomeCategoryList.value.toMutableList()
        val expenseList = _expanseCategoryList.value.toMutableList()
        useCaseExecutor.execute(deleteCategoryUseCase, category){
            if(category.type == CategoryType.INCOME)
                incomeList.remove(category)
            else
                expenseList.remove(category)
            _incomeCategoryList.value = incomeList
            _expanseCategoryList.value = expenseList
        }
    }

    fun addNewCategoryAction() {
        addCategory(
            Category(
                title = "hhjgggkg",
                type = CategoryType.INCOME,
                icon = R.drawable.budget
            )
        )
    }

    fun removeCategoryAction() {
        val list = _incomeCategoryList.value
        if(list.size > 0){
            removeCategory(list[list.size - 1])
        }
    }
}