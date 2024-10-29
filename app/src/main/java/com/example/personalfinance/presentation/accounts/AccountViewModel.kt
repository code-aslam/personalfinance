package com.example.personalfinance.presentation.accounts

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.domain.account.usecases.AddOrUpdateAccountUseCase
import com.example.personalfinance.domain.account.usecases.DeleteAccountUseCase
import com.example.personalfinance.domain.account.usecases.GetAccountsUseCase
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val getAllAccountsUseCase: GetAccountsUseCase,
    private val addOrUpdateAccountUseCase: AddOrUpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel(useCaseExecutor){

    private val _accountList = MutableStateFlow(mutableListOf<Account>())
    val accountList = _accountList.asStateFlow()

    private val _showDelete = MutableStateFlow(false)
    var showDelete = _showDelete.asStateFlow()

    private val _showEdit = MutableStateFlow(false)
    var showEdit = _showEdit.asStateFlow()

    init {
        updateAccounts()
    }

    private fun updateAccounts(){
        useCaseExecutor.execute(
            getAllAccountsUseCase,
            Unit,
            ::handleAccounts
        )
    }

    private fun handleAccounts(records: Flow<List<Account>>) {
        val accountList = _accountList.value
        viewModelScope.launch {
            records.collect { list ->
                list.forEach {account-> accountList.add(account)}
            }
            _accountList.value = accountList
        }
    }

    private fun addNewAccount(account: Account) {
        val accountList = _accountList.value.toMutableList()
        useCaseExecutor.execute(addOrUpdateAccountUseCase, account){
            account.id = it
            accountList.add(account)
            _accountList.value = accountList
        }
    }

    private fun removeAccount(account: Account) {
        val list = _accountList.value.toMutableList()
        useCaseExecutor.execute(deleteAccountUseCase, account){
            list.remove(account)
            _accountList.value = list
        }
    }

    private fun updateAccount(account: Account, index: Int){
        val list = _accountList.value.toMutableList()
        useCaseExecutor.execute(addOrUpdateAccountUseCase, account){
            account.id = it
            list[index] = account
            _accountList.value = list
        }
    }


    fun addNewAccountAction(account: Account){
        addNewAccount(account)
    }

    fun deleteAccountAction(account: Account){
        removeAccount(account)
    }

    fun updateAccountAction(account: Account, index : Int){
        updateAccount(account, index)
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

}