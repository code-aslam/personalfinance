package com.hotdogcode.spendwise.presentation.categories.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.common.formatMoney
import com.hotdogcode.spendwise.common.toRequiredFormat
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.presentation.accounts.components.AccountDetails
import com.hotdogcode.spendwise.presentation.accounts.components.ListItemAccountDetail
import com.hotdogcode.spendwise.presentation.categories.CategoryViewModel
import com.hotdogcode.spendwise.presentation.records.RecordsViewModel
import com.hotdogcode.spendwise.presentation.ui.components.BlankDialog
import com.hotdogcode.spendwise.presentation.ui.components.DetailHeader
import com.hotdogcode.spendwise.presentation.ui.components.Dialog
import com.hotdogcode.spendwise.presentation.ui.components.FinanceHeader
import com.hotdogcode.spendwise.presentation.ui.components.ItemWithIconTitleSubTitle
import com.hotdogcode.spendwise.ui.ListItemCategory
import com.hotdogcode.spendwise.ui.ListItemHeader
import com.hotdogcode.spendwise.ui.ListItemRecord
import com.hotdogcode.spendwise.ui.Toolbar
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SharpMainColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.onPrimary
import com.hotdogcode.spendwise.ui.theme.red

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    padding: PaddingValues,
    handleDrawer: () -> Unit,
    viewModel: CategoryViewModel
) {


    val recordsViewModel : RecordsViewModel = hiltViewModel()
    val incomeCategories by viewModel.incomeCategoryList.collectAsState()
    val expanseCategories by viewModel.expanseCategoryList.collectAsState()

    val showDetails by viewModel.showDetails.collectAsState()


    var isCalculationCompleted by remember { mutableStateOf(false) }
    val recordList by viewModel.dataRecords.collectAsState()
    val dataMap = remember {
        mutableStateMapOf(
            "INCOME SO FAR" to "calculating...",
            "EXPENSE SO FAR" to "calculating..."
        )
    }
    LaunchedEffect(recordList) {
        isCalculationCompleted = false
        var income = 0.0
        var expense = 0.0
        for (record in recordList) {
            if(record.transactionType == TransactionType.INCOME)
                income += record.amount
            if(record.transactionType == TransactionType.EXPENSE)
                expense += record.amount
        }
        dataMap["INCOME SO FAR"] = income.toString()
        dataMap["EXPENSE SO FAR"] = expense.toString()
        isCalculationCompleted = true
    }

    var selectedCategory by remember {
        mutableStateOf(
            Category(
                title = "",
                icon = IconName.SPORT,
                type = CategoryType.INCOME
            )
        )
    }
    var selectedIndex by remember { mutableIntStateOf(0) }

    val showDelete by viewModel.showDelete.collectAsState()
    val showEdit by viewModel.showEdit.collectAsState()
    val showAdd by viewModel.showAdd.collectAsState()
    val categoryIconList by viewModel.categoryIconList.collectAsState()



    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MainColor)
    ) {
        item {
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            FinanceHeader(dataMap, isCalculationCompleted)
        }
        item{
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
        }
        item {
            ListItemHeader(title = "Income Categories")
        }



        items(incomeCategories.size) { index ->
            ListItemCategory(
                category = incomeCategories[index],
                iconWidth = DpSize(40.dp, 40.dp),
                menuAction = {
                    selectedIndex = index
                    selectedCategory = incomeCategories[index]
                    when (it) {
                        "Edit" -> {
                            viewModel.showEditAction()
                        }
                        "Delete" -> {
                            viewModel.showDeleteAction()
                        }
                    }
                },
                onItemClick = {
                    selectedIndex = index
                    selectedCategory = incomeCategories[index]
                    viewModel.showDetailsAction()
                }
            )
        }

        item {
            ListItemHeader(title = "Expense Categories")
        }

        items(expanseCategories.size) { index ->
            ListItemCategory(
                category = expanseCategories[index],
                iconWidth = DpSize(40.dp, 40.dp),
                menuAction = {
                    selectedIndex = index
                    selectedCategory = expanseCategories[index]
                    when (it) {
                        "Edit" -> {viewModel.showEditAction()}
                        "Delete" -> {viewModel.showDeleteAction()}
                    }
                },
                onItemClick = {
                    selectedIndex = index
                    selectedCategory = expanseCategories[index]
                    viewModel.showDetailsAction()
                }
            )
        }


        item{
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, onPrimary, RoundedCornerShape(5.dp))
                        .clickable { viewModel.showAddAction() }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new category",
                        tint = brightGreen
                    )
                    Text(text = "Add New Category", color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }


        }
    }

    if(showDelete) {
        Dialog(
            title = "Delete This Category",
            content = {
                Text("Deleting this category will also delete all records and budgets for this category. Are you sure ?", color = Color.Black, fontSize = 18.sp)
            },
            confirmText = "YES",
            onConfirm = { viewModel.removeCategoryAction(selectedCategory)},
            dismissText = "NO",
            onDismiss = { viewModel.hideDeleteAction() }
        )
    }

    if(showEdit){
        var selectedIcon by remember { mutableStateOf(selectedCategory.icon) }
        var textValue by remember { mutableStateOf(selectedCategory.title) }

        Dialog(
            title = "Edit Category",
            content = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Name", modifier = Modifier.weight(1f),color = Color.Black)
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = { textValue = it },
                            modifier = Modifier
                                .weight(5f)
                                .background(Color.White, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),

                            )
                    }

                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Icon", color = Color.Black)
                        Box(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(5.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                                .padding(10.dp)
                        ){
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(categoryIconList.chunked(2)) { rowItems ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowItems.forEach { item ->
                                            Image(
                                                painter = painterResource(id = IconLib.getIcon(item)),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(if (selectedIcon == item) 52.dp else 50.dp)
                                                    .clip(CircleShape)
                                                    .border(
                                                        2.dp,
                                                        if (selectedIcon == item) DarkForestGreenColor else Color.White,
                                                        CircleShape
                                                    )
                                                    .clickable {
                                                        selectedIcon = item
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            },
            confirmText = "YES",
            onConfirm = {
                viewModel.updateCategoryAction(selectedCategory.copy(title = textValue, icon = selectedIcon), selectedIndex)
            },
            dismissText = "NO",
            onDismiss = { viewModel.hideEditAction() }
        )
    }

    if(showAdd){
        var textValue by remember { mutableStateOf("") }
        val types by remember {
            mutableStateOf(listOf(CategoryType.INCOME.title, CategoryType.EXPENSE.title))
        }
        var selectedType by remember {
            mutableStateOf(types[0])
        }
        var selectedIcon by remember {
            mutableStateOf(categoryIconList[0])
        }
        Dialog(
            title = "Add new category",
            content = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        types.forEach {
                                type -> Checkbox(checked = (selectedType == type),
                            colors = CheckboxDefaults.colors(
                                checkmarkColor = Color.White,
                                uncheckedColor = if(type == "Income") brightGreen else if(type =="Expense") red else Color.Black,
                                checkedColor = if(type == "Income") brightGreen else if(type =="Expense") red else Color.Black,
                            ),
                            onCheckedChange = {
                            selectedType = if(it) type else ""
                        })
                            Text(text = type, color = Color.Black)
                        }

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Name", modifier = Modifier.weight(1f),color = Color.Black)
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = { textValue = it },
                            modifier = Modifier
                                .weight(5f)
                                .background(Color.White, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = Color.White,
                                unfocusedContainerColor = Color.White
                            ),

                            )
                    }

                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Icon", color = Color.Black)
                        Box(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(5.dp))
                                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                                .padding(10.dp)
                        ){
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(categoryIconList.chunked(2)) { rowItems ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowItems.forEach { item ->
                                            Image(
                                                painter = painterResource(id = IconLib.getIcon(item)),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(if (selectedIcon == item) 52.dp else 50.dp)
                                                    .clip(CircleShape)
                                                    .border(
                                                        2.dp,
                                                        if (selectedIcon == item) Color.Black else Color.White,
                                                        CircleShape
                                                    )
                                                    .clickable {
                                                        selectedIcon = item
                                                    }
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                }
            },
            confirmText = "SAVE",
            onConfirm = { viewModel.addNewCategoryAction(
                Category(
                    title = textValue,
                    icon = selectedIcon,
                    type = when(selectedType){
                        CategoryType.INCOME.title -> CategoryType.INCOME
                        else -> CategoryType.EXPENSE
                    }
                )
            )},
            dismissText = "CANCEL",
            onDismiss = { viewModel.hideAddAction()  }
        )
    }

    BlankDialog(
        showDialog = showDetails,
        onDismiss = {
            viewModel.hideDetailsAction()
        }
    ) {
        CategoryDetails(selectedCategory,
            onDismiss = {
                viewModel.hideDetailsAction()
            })
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryDetails(selectedCategory: Category,
                   onDismiss: () -> Unit)
{

    val recordsViewModel : RecordsViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        recordsViewModel.getRecordsForCategory(selectedCategory.id)
    }
    val recordList by recordsViewModel.dateSortedRecordsForCategory.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MainColor),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            stickyHeader {
                DetailHeader("Category details"){
                    onDismiss()
                }
            }
            item {
                ItemWithIconTitleSubTitle(
                    icon = selectedCategory.icon,
                    title = selectedCategory.title,
                    smallTitle = selectedCategory.type.typeName
                )
            }

            recordList.forEach { (localDate, recordWithCategoryAndAccounts) ->
                item {
                    Column(
                        modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                    ) {
                        Text(text = localDate.toRequiredFormat())
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(
                                DarkForestGreenColor
                            ))
                    }
                }
                items(recordWithCategoryAndAccounts.size) { index ->
                    ListItemCategoryDetail(
                        iconWidth = DpSize(35.dp, 35.dp),
                        record = recordWithCategoryAndAccounts[index],
                        onItemClick = {
                            //onItemClick(recordWithCategoryAndAccounts[index])
                        })
                }
                item{
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp))
                }

            }

        }
    }

}


