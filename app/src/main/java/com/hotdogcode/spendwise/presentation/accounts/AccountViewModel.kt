package com.hotdogcode.spendwise.presentation.accounts

import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.account.usecases.AddOrUpdateAccountUseCase
import com.hotdogcode.spendwise.domain.account.usecases.DeleteAccountUseCase
import com.hotdogcode.spendwise.domain.account.usecases.GetAccountsUseCase
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordsUseCase
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _showDetails = MutableStateFlow(false)
    var showDetails = _showDetails.asStateFlow()

    private val _accountIconList = MutableStateFlow(mutableListOf<IconName>())
    val accountIconList = _accountIconList.asStateFlow()




    init {
        fetchAccounts()
        addAccountIcon()
        fetchRecords()
    }


    private fun addAccountIcon(){
        _accountIconList.value.clear()
        _accountIconList.value.addAll(
            arrayOf<IconName>(
                IconName.AWARD,
                IconName.BABY,
                IconName.BEAUTY,
                IconName.BILLS,
                IconName.CAR,
                IconName.CLOTHING,
                IconName.COUPONS,
                IconName.EDUCATION,
                IconName.ELECTRONICS,
                IconName.ENTERTAINMENT,
                IconName.FOOD,
                IconName.GRANTS,
                IconName.HEALTH,
                IconName.HOME,
                IconName.INSURANCE,
                IconName.LOTTERY,
                IconName.REFUNDS,
                IconName.RENTALS,
                IconName.SALARY,
                IconName.SALE,
                IconName.SHOPPING,
                IconName.SOCIAL,
                IconName.SPORT
            ).toMutableList())
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

    fun showDetailsAction() {
        _showDetails.value = true
    }

    fun hideDetailsAction() {
        _showDetails.value = false
    }

}