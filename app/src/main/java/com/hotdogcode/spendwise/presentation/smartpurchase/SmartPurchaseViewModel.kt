package com.hotdogcode.spendwise.presentation.smartpurchase

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableDoubleState
import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.common.AccountType
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.account.usecases.GetAccountsUseCase
import com.hotdogcode.spendwise.domain.category.usecases.GetCategoriesUseCase
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordsUseCase
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import com.hotdogcode.spendwise_ml.PurchaseSuggestion
import com.hotdogcode.spendwise_ml.PurchaseTransaction
import com.hotdogcode.spendwise_ml.SpendWisePurchaseAdviser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class SmartPurchaseViewModel @Inject constructor(
    useCaseExecutor: UseCaseExecutor,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getAccountsUseCase: GetAccountsUseCase,
    private val getRecordsUseCase: GetRecordsUseCase,
    private val spendWisePurchaseAdviser: SpendWisePurchaseAdviser
) : BaseViewModel(useCaseExecutor){


    private val _purchaseSuggestion = MutableStateFlow(PurchaseSuggestion(
        isGoodPurchase = false,
        confidence = 0.0f,
        reason = ""
    ))
    val purchaseSuggestion = _purchaseSuggestion.asStateFlow()

    private val _dateSortedRecords = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecords = _dateSortedRecords.asStateFlow()

    private val _expanseCategoryList = MutableStateFlow(mutableListOf<Category>())
    val expanseCategoryList = _expanseCategoryList.asStateFlow()

    private val _accountList = MutableStateFlow(mutableListOf<Account>())
    val accountList = _accountList.asStateFlow()

    private val _accountBalance = MutableStateFlow(0.0)
    val accountBalance = _accountBalance.asStateFlow()

    private val _creditBalance = MutableStateFlow(0.0)
    val creditBalance = _creditBalance.asStateFlow()

    private val _monthlyIncome = MutableStateFlow(0.0)
    val monthlyIncome = _monthlyIncome.asStateFlow()

    private val _monthlyExpense = MutableStateFlow(0.0)
    val monthlyExpense = _monthlyExpense.asStateFlow()

    private val _categorySpendingAvg = MutableStateFlow(0.0)
    val categorySpendingAvg = _categorySpendingAvg.asStateFlow()

    init {
        //Log.e("Aslam", spendWisePurchaseAdviser.analyzePurchase().isGoodPurchase.toString())
        fetchCategories()
        fetchAccounts()
        fetchRecords()
    }

    fun getAccountBalance(selected : String){
        val accountType = _accountList.value.filter { it.name == selected }.map { it.type }[0]
        if(accountType == AccountType.BANK_ACCOUNT) {
            _accountBalance.value = _accountList.value.filter { it.name == selected }
                .sumOf { it.balance + it.initialAmount }
            _creditBalance.value = 0.0
        }else{
            _accountBalance.value = 0.0
            _creditBalance.value = _accountList.value.filter { it.name == selected }
                .sumOf { it.balance + it.initialAmount }
        }
    }

    fun getCategorySpendingAcg(selected: String){
         val selectedCatId = _expanseCategoryList.value.filter { it.title == selected }.map { it.id }[0]

         _dateSortedRecords.value.forEach {
             map->
             val count = map.value.filter { it.categoryId == selectedCatId }.size
             var total = 0.0
             total += map.value.filter {
                it.categoryId == selectedCatId
            }
                .sumOf {
                it.amount
            }
             _categorySpendingAvg.value = total/count
        }
    }



    /**
     * features = ["account_balance", "credit_limit", "monthly_income", "monthly_expense",
     *             "debt_to_income_ratio", "category_spending_avg", "day_of_week", "hour_of_day", "amount"]
     */
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
                        _expanseCategoryList.value = expanse.toMutableList()
                    }
                }
            }
        }
    }


    fun fetchAccounts() {
        try {
            viewModelScope.launch {
                useCaseExecutor.execute(
                    getAccountsUseCase,
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

    @RequiresApi(Build.VERSION_CODES.O)
    private fun fetchRecords(){
        viewModelScope.launch {
            useCaseExecutor.execute(
                getRecordsUseCase,
                Unit
            ){
                    records ->
                viewModelScope.launch {
                    records.collect { list ->
                        _dateSortedRecords.value = groupDataByDaySorted(list).toMutableMap()
                        _monthlyIncome.value += list.filter { it.transactionType == TransactionType.INCOME }.sumOf { it.amount }
                        _monthlyExpense.value += list.filter { it.transactionType == TransactionType.EXPENSE }.sumOf { it.amount }
                    }
                }
            }
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupDataByDaySorted(dataList: List<RecordWithCategoryAndAccount>): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        return dataList.groupBy {
            val instant = Instant.ofEpochMilli(it.date)
            instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }

    fun buildPurchaseTransaction(amount : Int) {
        _purchaseSuggestion.value = spendWisePurchaseAdviser.analyzePurchase(PurchaseTransaction(
            accountBalance = _accountBalance.value,
            creditLimit = _creditBalance.value,
            monthlyIncome = _monthlyIncome.value,
            monthlyExpense = _monthlyExpense.value,
            categorySpendingAvg = _categorySpendingAvg.value,
            amount = amount
        ))
    }
}