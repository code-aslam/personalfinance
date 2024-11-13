package com.example.personalfinance.presentation.createrecord

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CreateRecordViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {
    private val _selectedAccount = MutableStateFlow(Account(
        name = "Account",
        icon = R.drawable.walletbig,
        initialAmount = 0
    ))
    val selectedAccount = _selectedAccount.asStateFlow()

    private val _selectedAccountForCategory = MutableStateFlow(Account(
        name = "Account",
        icon = R.drawable.walletbig,
        initialAmount = 0
    ))
    val selectedAccountForCategory = _selectedAccountForCategory.asStateFlow()

    private val _selectedCategory = MutableStateFlow(Category(
        title = "Category",
        icon = R.drawable.price,
        type = CategoryType.INCOME
    ))
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _transactionType = MutableStateFlow(false)
    val transactionType = _transactionType.asStateFlow()

    fun updateAccount(newAccount : Account){
        _selectedAccount.value = newAccount
    }

    fun updateCategory(newCategory : Category){
        _selectedCategory.value = newCategory
    }



    fun updateTransferType(newValue : Boolean){
        _transactionType.value = newValue
    }
}