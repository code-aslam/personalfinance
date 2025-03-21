package com.hotdogcode.spendwise.presentation.createrecord.components

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.common.fromJsonStringToRecordWithCategoryAndAccount
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.record.entity.Record
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.presentation.accounts.AccountViewModel
import com.hotdogcode.spendwise.presentation.categories.CategoryViewModel
import com.hotdogcode.spendwise.presentation.createrecord.CreateRecordViewModel
import com.hotdogcode.spendwise.presentation.records.RecordsViewModel
import com.hotdogcode.spendwise.presentation.ui.components.DatePickerModal
import com.hotdogcode.spendwise.presentation.ui.components.TimePickerModel
import com.hotdogcode.spendwise.ui.ListItemCreateRecordAccount
import com.hotdogcode.spendwise.ui.ListItemCreateRecordCategory
import com.hotdogcode.spendwise.ui.ListItemHeaderAccount
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.PBGFont
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SharpMainColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.googlelightgray
import com.hotdogcode.spendwise.ui.theme.googlelightgray2
import com.hotdogcode.spendwise.ui.theme.onPrimary
import com.hotdogcode.spendwise.ui.theme.red
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateRecordScreen(
    mainNavController: NavHostController,
    accountViewModel: AccountViewModel,
    recordsViewModel: RecordsViewModel,
    categoryViewModel: CategoryViewModel,
    createRecordViewModel: CreateRecordViewModel,
    selectedRecord : String? = null
){

    LaunchedEffect(Unit) {
        accountViewModel.fetchAccounts()
        categoryViewModel.fetchCategories()
    }
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(MainColor)) { innerPadding ->
        CreateRecord(accountViewModel,
            recordsViewModel,
            categoryViewModel,
            createRecordViewModel,
            innerPadding,
            mainNavController,
            selectedRecord)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecord(
    accountViewModel: AccountViewModel,
    recordsViewModel: RecordsViewModel,
    categoryViewModel: CategoryViewModel,
    createRecordViewModel: CreateRecordViewModel,
    paddingValues: PaddingValues,
    mainNavController: NavHostController,
    selectedRecord: String?) {
    val context = LocalContext.current
    val categoriesIncome by categoryViewModel.incomeCategoryList.collectAsState()
    val categoriesExpanse by categoryViewModel.expanseCategoryList.collectAsState()
    val bottomSheetStateAccount = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheetStateCategory = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val selectedAccount by createRecordViewModel.selectedAccount.collectAsState()
    val selectedCategory by createRecordViewModel.selectedCategory.collectAsState()
    val transferType by createRecordViewModel.transactionType.collectAsState()
    val scope = rememberCoroutineScope()
    val types by remember { mutableStateOf(listOf(TransactionType.INCOME.name, TransactionType.EXPENSE.name, TransactionType.TRANSFER.name)) }
    var selectedType by remember { mutableStateOf(types[0]) }
    val result by createRecordViewModel.result.collectAsState()
    val symbol by createRecordViewModel.symbol.collectAsState()
    var textNotes by remember { mutableStateOf("") }
    var textAmountSecond by remember { mutableStateOf("") }

    val selectedDate by createRecordViewModel.selectedDate.collectAsState()
    val selectedDateMills by createRecordViewModel.selectedDateMills.collectAsState()

    val selectedTime by createRecordViewModel.selectedTime.collectAsState()
    val selectedRecordId by createRecordViewModel.selectedRecordId.collectAsState()
    var recordFrom by remember { mutableStateOf("Account") }
    recordFrom = when(selectedType){
        "INCOME", "EXPENSE" -> "Account"
        else -> "From"
    }
    var recordTo by remember { mutableStateOf("Category") }
    recordTo = when(selectedType){
        "INCOME", "EXPENSE" -> "Category"
        else -> "To"
    }

    LaunchedEffect(selectedRecord?.isNotEmpty()) {
        selectedRecord?.let {
            val recordWithCategoryAndAccount = it.fromJsonStringToRecordWithCategoryAndAccount()
            recordWithCategoryAndAccount?.let {
                record->
                selectedType = record.transactionType.name
                createRecordViewModel.updateResult(record.amount.toString())
                createRecordViewModel.updateAccount(Account(
                    id = record.accountId,
                    name = record.accountName,
                    initialAmount = record.accountInitialAmount,
                    icon = record.accountIcon,
                    type = record.accountType
                ))
                createRecordViewModel.updateCategory(Category(
                    id = record.categoryId,
                    title = record.categoryTitle,
                    icon = record.categoryIcon,
                    type = record.categoryType
                ))
                textNotes = record.notes
                createRecordViewModel.updateDate(record.date)
                createRecordViewModel.updateTime(record.time)
                createRecordViewModel.updateRecordId(record.recordId)
            }

        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                bottom = paddingValues.calculateBottomPadding()
            )
            .background(MainColor)
    ) {
        // Cancel and save button
        Row(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ) {
            IconButton(onClick = {
                mainNavController.popBackStack()
            },
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Cancel",
                        tint = Color.Black
                    )
                    Text(text = "Cancel", color = Color.Black)
                }
            }

            IconButton(onClick = {
                try {
                    if(selectedAccount.id == 0L){
                        Toast.makeText(context, "Please Select Account", Toast.LENGTH_SHORT).show()
                    }else if(selectedCategory.id == 0L){
                        Toast.makeText(context, "Please Select Category", Toast.LENGTH_SHORT).show()
                    }else if(result.toDouble() == 0.0){
                        Toast.makeText(context, "Please Enter Amount", Toast.LENGTH_SHORT).show()
                    }else {
                        recordsViewModel.addNewRecord(
                            Record(
                                id = selectedRecordId,
                                transactionType = if (selectedType == "INCOME") TransactionType.INCOME else TransactionType.EXPENSE,
                                categoryId = selectedCategory.id,
                                accountId = selectedAccount.id,
                                date = selectedDateMills,
                                time = selectedTime,
                                amount = result.toDouble(),
                                notes = textNotes
                            ),
                            selectedAccount
                        )
                        mainNavController.popBackStack()
                    }
                }catch (e : Exception){
                    Toast.makeText(context, "Error, Try again", Toast.LENGTH_SHORT).show()
                }

            }, modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = brightGreen
                    )
                    Text(text = "Save", color = brightGreen)
                }
            }
        }

        // INCOME, EXPANSE, TRANSFER Transaction type
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            types.forEach {
                    type ->
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Checkbox(
                        modifier = Modifier
                            .padding(1.dp)
                            .size(30.dp),
                        colors = CheckboxDefaults.colors(
                            checkmarkColor = Color.White,
                            uncheckedColor = if(type == "INCOME") brightGreen else if(type =="EXPENSE") red else Color.Black,
                            checkedColor = if(type == "INCOME") brightGreen else if(type =="EXPENSE") red else Color.Black,
                        ),
                        checked = (selectedType == type),
                        onCheckedChange = {
                            //selectedType = if(it) type else ""
                        }
                    )
                    Text(text = type,
                        fontSize = 14.sp,
                        color = Color.Black,
                        modifier = Modifier.clickable {
                            selectedType = type
                            createRecordViewModel.updateCategory(Category.testData())
                        })
                }

            }
        }

        // Account and Category chooser (Bottom sheet with lazy column )
        Row (
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ){
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = recordFrom, color = Color.Black, fontSize = 14.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, onPrimary, RoundedCornerShape(5.dp))
                        .clickable {
                            scope.launch {
                                bottomSheetStateAccount.show()
                                bottomSheetStateCategory.hide()
                            }
                        }
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = IconLib.getIcon(selectedAccount.icon)),
                        colorFilter = if(selectedAccount.icon == IconName.WALLET_BIG)
                            ColorFilter.tint(Color.Black) else ColorFilter.tint(Color.Transparent, BlendMode.Color),
                        contentDescription = selectedAccount.name,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                    )
                    Text(text = selectedAccount.name, color = Color.Black)
                }

            }
            Spacer(modifier = Modifier.width(2.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = recordTo, fontSize = 14.sp, modifier = Modifier.padding(4.dp), color = Color.Black, fontWeight = FontWeight.Bold)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, onPrimary, RoundedCornerShape(5.dp))
                        .clickable {
                            scope.launch {
                                bottomSheetStateCategory.show()
                                bottomSheetStateAccount.hide()
                            }
                        }
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = IconLib.getIcon(selectedCategory.icon)),
                        contentDescription = "Category",
                        colorFilter = if(selectedCategory.icon == IconName.CATEGORY_BIG)
                            ColorFilter.tint(Color.Black) else ColorFilter.tint(Color.Transparent, BlendMode.Color),
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Text(text = selectedCategory.title, color = Color.Black)
                }

            }
        }

        Spacer(modifier = Modifier
            .height(4.dp)
            .fillMaxWidth())

        Column(
            modifier = Modifier.padding(start = 10.dp, end = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                OutlinedTextField(
                    value = textNotes,
                    placeholder = {Text(text = "Add notes")},
                    onValueChange = { textNotes = it },
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, onPrimary, RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = googlelightgray,
                        unfocusedContainerColor = googlelightgray
                    ),
                )
            }

            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
                modifier = Modifier
                    .background(
                        color = MainColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .border(1.dp, onPrimary, RoundedCornerShape(4.dp))
                    .weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = symbol, fontSize = 40.sp, modifier = Modifier.weight(2f))
                Text(
                    text = result,
                    modifier = Modifier
                        .weight(8f),
                    textAlign = TextAlign.End,
                    fontSize = 40.sp
                )
                IconButton(onClick = { createRecordViewModel.processDelete() }, modifier = Modifier.weight(2f)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.backspace),
                        contentDescription = "Clear amount one at a time",
                        tint = red
                    )
                }

            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { createRecordViewModel.processCalculation('+') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = onPrimary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "+", fontSize = 30.sp, color = brightGreen)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('7') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "7", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('8') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "8", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('9') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "9", fontSize = 30.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { createRecordViewModel.processCalculation('-') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = onPrimary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "-", fontSize = 30.sp, color = brightGreen)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('4') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "4", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('5') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "5", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('6') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),

                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "6", fontSize = 30.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { createRecordViewModel.processCalculation('*') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = onPrimary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "*", fontSize = 30.sp, color = brightGreen)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('1') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "1", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('2') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "2", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('3') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "3", fontSize = 30.sp, color = Color.Black)
                }
            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { createRecordViewModel.processCalculation('/') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = onPrimary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "/", fontSize = 30.sp, color = brightGreen)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('0') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "0", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('.') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = ".", fontSize = 30.sp, color = Color.Black)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('=') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = onPrimary,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, onPrimary, RoundedCornerShape(4.dp))
                ) {
                    Text(text = "=", fontSize = 30.sp, color = brightGreen)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { createRecordViewModel.setDatePicker(true) }, modifier = Modifier.weight(1f)) {
                    Text(text = selectedDate, fontSize = 20.sp, color = Color.Black)
                }
                TextButton(onClick = { createRecordViewModel.setTimePicker(true) }, modifier = Modifier.weight(1f)) {
                    Text(text = selectedTime, fontSize = 20.sp, color = Color.Black)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.padding(paddingValues),
        sheetState = bottomSheetStateAccount,
        sheetContent = {
            BottomSheetContentAccount(createRecordViewModel,accountViewModel,onDismiss = {
                scope.launch { bottomSheetStateAccount.hide() }
            })
        }
    ) {
        // Additional content if needed
    }

    ModalBottomSheetLayout(
        modifier = Modifier.padding(paddingValues),
        sheetState = bottomSheetStateCategory,
        sheetContent = {
            when(selectedType){
                "INCOME" -> BottomSheetContentCategory(categoriesIncome,createRecordViewModel,onDismiss = {
                    scope.launch { bottomSheetStateCategory.hide() }
                })
                "EXPENSE" -> BottomSheetContentCategory(categoriesExpanse,createRecordViewModel,onDismiss = {
                    scope.launch { bottomSheetStateCategory.hide() }
                })
                "TRANSFER" -> BottomSheetContentAccount(createRecordViewModel,accountViewModel,true,onDismiss = {
                    scope.launch { bottomSheetStateAccount.hide() }
                })
            }
        }
    ) {
        // Additional content if needed
    }


    DatePickerModal(onDateSelected = {
        selectedDateMills -> createRecordViewModel.updateDate(selectedDateMills)
    }, onDismiss = {
        createRecordViewModel.setDatePicker(false)
    }, createRecordViewModel = createRecordViewModel)


    TimePickerModel(onTimeSelected = {
            time -> createRecordViewModel.updateTime(time)
    }, onDismiss = {
        createRecordViewModel.setTimePicker(false)
    }, createRecordViewModel = createRecordViewModel)
}



@Composable
fun BottomSheetContentAccount(createRecordViewModel: CreateRecordViewModel,
                              accountViewModel: AccountViewModel,
                              fromCategoryChooser : Boolean = false,
                              onDismiss: () -> Unit) {
    val accounts by accountViewModel.accountList.collectAsState()
    val transactionType by createRecordViewModel.transactionType.collectAsState()
    createRecordViewModel.updateTransferType(fromCategoryChooser)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor)
    ) {

        item {
            ListItemHeaderAccount(title = "Select an account")
        }

        items(accounts.size) { index ->
            ListItemCreateRecordAccount(
                iconWidth = DpSize(45.dp, 45.dp),
                account = accounts[index],
                onItemClick = {
                    if(fromCategoryChooser){

                    }else{
                        createRecordViewModel.updateAccount(it)
                    }
                    onDismiss()
                }
            )
        }
    }
}

@Composable
fun BottomSheetContentCategory(categories: List<Category>, createRecordViewModel: CreateRecordViewModel, onDismiss: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ListItemHeaderAccount(title = "Select a category")
        }
        items(categories.chunked(3)){
            rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                rowItems.forEach {
                    category ->
                    Box(modifier = Modifier
                        .background(MainColor)
                        .weight(1f),
                        contentAlignment = Alignment.Center){
                        ListItemCreateRecordCategory(
                            iconWidth = DpSize(30.dp, 30.dp),
                            category = category,
                            onItemClick = {
                                createRecordViewModel.updateCategory(it)
                                onDismiss()
                            }
                        )
                    }

                }
                if (rowItems.size < 3) {
                    repeat(3 - rowItems.size) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }

        }
    }
}