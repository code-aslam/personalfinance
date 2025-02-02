package com.example.personalfinance.presentation.accounts

import androidx.lifecycle.viewModelScope
import com.example.personalfinance.R
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import com.example.personalfinance.domain.account.usecases.AddOrUpdateAccountUseCase
import com.example.personalfinance.domain.account.usecases.DeleteAccountUseCase
import com.example.personalfinance.domain.account.usecases.GetAccountsUseCase
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.example.personalfinance.domain.record.usecases.GetRecordsUseCase
import com.example.personalfinance.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val getAllAccountsUseCase: GetAccountsUseCase,
    private val addOrUpdateAccountUseCase: AddOrUpdateAccountUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseViewModel(useCaseExecutor) {

    var text = "Hello"

    private val _dataRecords = MutableStateFlow(mutableListOf<RecordWithCategoryAndAccount>())
    val dataRecords = _dataRecords.asStateFlow()

    private val _accountList = MutableStateFlow(mutableListOf<Account>())
    val accountList = _accountList.asStateFlow()

    private val _showDelete = MutableStateFlow(false)
    var showDelete = _showDelete.asStateFlow()

    private val _showEdit = MutableStateFlow(false)
    var showEdit = _showEdit.asStateFlow()

    private val _showAdd = MutableStateFlow(false)
    var showAdd = _showAdd.asStateFlow()

    private val _accountIconList = MutableStateFlow(mutableListOf<Int>())
    val accountIconList = _accountIconList.asStateFlow()


    init {
        fetchAccounts()
        addAccountIcon()
        fetchRecords()
    }


    private fun addAccountIcon() {
        _accountIconList.value.clear()
        _accountIconList.value.addAll(
            intArrayOf(
                R.drawable.cashicon,
                R.drawable.cardicon,
                R.drawable.savingsicon
            ).toMutableList()
        )
    }

    private fun fetchRecords() {
        try {
            viewModelScope.launch {
                useCaseExecutor.execute(
                    getRecordsUseCase,
                    Unit
                ) { records ->
                    viewModelScope.launch {
                        records.collect { list ->
                            _dataRecords.value = list.toMutableList()
                        }
                    }
                }

            }
        } catch (ignore: CancellationException) {
        } catch (_: Throwable) {
        }
    }

    fun fetchAccounts() {
        try {
            viewModelScope.launch {
                useCaseExecutor.execute(
                    getAllAccountsUseCase,
                    Unit
                ) { accounts ->
                    viewModelScope.launch {
                        accounts.collect { list ->
                            _accountList.value = list.toMutableList()
                        }
                    }

                }

            }
        } catch (ignore: CancellationException) {
        } catch (_: Throwable) {
        }

    }

    private fun addNewAccount(account: Account) {
        try {
            viewModelScope.launch {
                useCaseExecutor.execute(addOrUpdateAccountUseCase, account) {}
            }
        } catch (ignore: CancellationException) {
        } catch (_: Throwable) {
        }
    }

    private fun removeAccount(account: Account) {
        viewModelScope.launch {
            useCaseExecutor.execute(deleteAccountUseCase, account) {}
        }
    }

    private fun updateAccount(account: Account, index: Int) {
        viewModelScope.launch {
            useCaseExecutor.execute(addOrUpdateAccountUseCase, account) {}
        }

    }


    fun addNewAccountAction(account: Account) {
        addNewAccount(account)
    }

    fun deleteAccountAction(account: Account) {
        removeAccount(account)
    }

    fun updateAccountAction(account: Account, index: Int) {
        updateAccount(account, index)
    }

    fun showDeleteAction() {
        _showDelete.value = true
    }

    fun hideDeleteAction() {
        _showDelete.value = false
    }

    fun showEditAction() {
        _showEdit.value = true
    }

    fun hideEditAction() {
        _showEdit.value = false
    }

    fun showAddAction() {
        _showAdd.value = true
    }

    fun hideAddAction() {
        _showAdd.value = false
    }

}