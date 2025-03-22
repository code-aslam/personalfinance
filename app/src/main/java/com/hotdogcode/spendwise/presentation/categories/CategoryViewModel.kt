package com.hotdogcode.spendwise.presentation.categories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewModelScope
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.domain.category.usecases.AddOrUpdateCategoryUseCase
import com.hotdogcode.spendwise.domain.category.usecases.DeleteCategoryUseCase
import com.hotdogcode.spendwise.domain.category.usecases.GetCategoriesUseCase
import com.hotdogcode.spendwise.domain.cleanarchitecture.usecase.UseCaseExecutor
import com.hotdogcode.spendwise.domain.record.usecases.GetRecordsUseCase
import com.hotdogcode.spendwise.presentation.cleanarchitecture.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import javax.inject.Inject
import kotlin.coroutines.cancellation.CancellationException

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val addOrUpdateCategoryUseCase: AddOrUpdateCategoryUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val getRecordsUseCase: GetRecordsUseCase,
    useCaseExecutor: UseCaseExecutor
) : BaseViewModel(useCaseExecutor) {

    private val _dataRecords = MutableStateFlow(mutableListOf<RecordWithCategoryAndAccount>())
    val dataRecords = _dataRecords.asStateFlow()

    private val _dateSortedRecords = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())

    private val _dateSortedRecordsPerMonth = MutableStateFlow(mutableMapOf<LocalDate, List<RecordWithCategoryAndAccount>>())
    val dateSortedRecordsPerMonth = _dateSortedRecordsPerMonth.asStateFlow()

    private val _incomeCategoryList = MutableStateFlow(mutableListOf<Category>())
    val incomeCategoryList = _incomeCategoryList.asStateFlow()

    private val _expanseCategoryList = MutableStateFlow(mutableListOf<Category>())
    val expanseCategoryList = _expanseCategoryList.asStateFlow()

    private val _categoryIconList = MutableStateFlow(mutableListOf<IconName>())
    val categoryIconList = _categoryIconList.asStateFlow()

    private val _showDelete = MutableStateFlow(false)
    var showDelete = _showDelete.asStateFlow()

    private val _showEdit = MutableStateFlow(false)
    var showEdit = _showEdit.asStateFlow()

    private val _showAdd = MutableStateFlow(false)
    var showAdd = _showAdd.asStateFlow()

    private val _showDetails = MutableStateFlow(false)
    var showDetails = _showDetails.asStateFlow()

    private val _currentDate = MutableStateFlow(Calendar.getInstance())
    var currentDate = _currentDate.asStateFlow()

    init {
        fetchCategories()
        addCategoryIcon()
        fetchRecords()
    }

    private fun addCategoryIcon(){
        _categoryIconList.value.clear()
        _categoryIconList.value.addAll(
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
                            _dateSortedRecords.value = groupDataByDaySorted(list).toMutableMap()
                            updateData()
                        }
                    }
                }

            }
        } catch (ignore: CancellationException) {
        } catch (_: Throwable) {
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun groupDataByDaySorted(dataList: List<RecordWithCategoryAndAccount>): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        return dataList.groupBy {
            val instant = Instant.ofEpochMilli(it.date)
            instant.atZone(ZoneId.systemDefault()).toLocalDate()
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }

    fun updateCurrentMonth(months : Int){
        val newDate = _currentDate.value.clone() as Calendar // Create a new instance
        newDate.add(Calendar.MONTH, months) // Modify the new instance
        _currentDate.value = newDate // Update the state with the new instance
        updateData()
    }

    private fun filterRecordsByMonth(
        recordsMap: Map<LocalDate, List<RecordWithCategoryAndAccount>>,
        date: Date
    ): Map<LocalDate, List<RecordWithCategoryAndAccount>> {
        // Convert the input Date to LocalDate
        val inputLocalDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()

        // Filter the map to include only entries for the same month and year
        return recordsMap.filter { (key, _) ->
            key.year == inputLocalDate.year && key.month == inputLocalDate.month
        }.toSortedMap { date1, date2 -> date2.compareTo(date1) }
    }

    private fun updateData() {
        _dateSortedRecordsPerMonth.value =
            filterRecordsByMonth(_dateSortedRecords.value, _currentDate.value.time).toMutableMap()
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


    fun showDetailsAction() {
        _showDetails.value = true
    }

    fun hideDetailsAction() {
        _showDetails.value = false
    }

}