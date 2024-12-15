package com.example.personalfinance.presentation.categories

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.domain.category.usecases.AddOrUpdateCategoryUseCase
import com.example.personalfinance.domain.category.usecases.DeleteCategoryUseCase
import com.example.personalfinance.domain.category.usecases.GetCategoriesUseCase
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val addOrUpdateCategoryUseCase: AddOrUpdateCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {

    private val _incomeCategoryList = MutableStateFlow(mutableListOf<Category>())
    val incomeCategoryList = _incomeCategoryList.asStateFlow()

    private val _expanseCategoryList = MutableStateFlow(mutableListOf<Category>())
    val expanseCategoryList = _expanseCategoryList.asStateFlow()

    private val _categoryIconList = MutableStateFlow(mutableListOf<Int>())
    val categoryIconList = _categoryIconList.asStateFlow()

    private val _showDelete = MutableStateFlow(false)
    var showDelete = _showDelete.asStateFlow()

    private val _showEdit = MutableStateFlow(false)
    var showEdit = _showEdit.asStateFlow()

    private val _showAdd = MutableStateFlow(false)
    var showAdd = _showAdd.asStateFlow()

    init {
        fetchCategories()
        addCategoryIcon()
    }

    private fun addCategoryIcon(){
        _categoryIconList.value.clear()
        _categoryIconList.value.addAll(
            intArrayOf(
                R.drawable.gifticon,
                R.drawable.awardicon,
                R.drawable.homeicon,
                R.drawable.refundicon,
                R.drawable.walleticon
            ).toMutableList())
    }



    fun fetchCategories(){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getCategoriesUseCase,
                Unit
            ){
                categories ->
                viewModelScope.launch {
                    categories.collect { list ->
                        val (income, expanse) = list.partition { it.type == CategoryType.INCOME }
                        _incomeCategoryList.value = income.toMutableList()
                        _expanseCategoryList.value = expanse.toMutableList()
                    }
                }
            }
        }

    }


    private fun addNewCategory(category: Category) {
        viewModelScope.launch {
            useCaseExecutor.execute(addOrUpdateCategoryUseCase, category){}
        }

    }

    private fun updateCategory(category: Category, index: Int){
        viewModelScope.launch {
            useCaseExecutor.execute(addOrUpdateCategoryUseCase, category){}
        }

    }



    private fun removeCategory(category: Category) {
        viewModelScope.launch {
            useCaseExecutor.execute(deleteCategoryUseCase, category){}
        }

    }

    fun addNewCategoryAction(category: Category) {
        addNewCategory(category = category)
    }

    fun updateCategoryAction(category: Category, index: Int){
        updateCategory(category, index)
    }

    fun removeCategoryAction(category: Category) {
        removeCategory(category)
    }

    fun showDeleteAction(){
        _showDelete.value = true
    }
    fun hideDeleteAction(){
        _showDelete.value = false
    }

    fun showEditAction(){
        _showEdit.value = true
    }

    fun hideEditAction(){
        _showEdit.value = false
    }

    fun showAddAction(){
        _showAdd.value = true
    }

    fun hideAddAction(){
        _showAdd.value = false
    }
}