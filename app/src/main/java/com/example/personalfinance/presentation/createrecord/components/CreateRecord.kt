package com.example.personalfinance.presentation.createrecord.components

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.personalfinance.R
import com.example.personalfinance.common.TransactionType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.createrecord.CreateRecordViewModel
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.presentation.ui.components.DatePickerModal
import com.example.personalfinance.presentation.ui.components.TimePickerModel
import com.example.personalfinance.ui.ListItemAccount
import com.example.personalfinance.ui.ListItemCategory
import com.example.personalfinance.ui.ListItemCreateRecordAccount
import com.example.personalfinance.ui.ListItemCreateRecordCategory
import com.example.personalfinance.ui.ListItemHeaderAccount
import com.example.personalfinance.ui.theme.AccentColor
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.LightBeige
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.PBGFont
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SharpMainColor
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun CreateRecordScreen(
    mainNavController: NavHostController,
    accountViewModel: AccountViewModel,
    recordsViewModel: RecordsViewModel,
    categoryViewModel: CategoryViewModel
){

    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(MainColor)) { innerPadding ->
        CreateRecord(accountViewModel,recordsViewModel,categoryViewModel,innerPadding, mainNavController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateRecord(accountViewModel: AccountViewModel, recordsViewModel: RecordsViewModel, categoryViewModel: CategoryViewModel, paddingValues: PaddingValues, mainNavController: NavHostController) {
    val createRecordViewModel : CreateRecordViewModel = hiltViewModel()

    val categoriesIncome by categoryViewModel.incomeCategoryList.collectAsState()
    val categoriesExpanse by categoryViewModel.expanseCategoryList.collectAsState()
    val bottomSheetStateAccount = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheetStateCategory = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val selectedAccount by createRecordViewModel.selectedAccount.collectAsState()
    val selectedCategory by createRecordViewModel.selectedCategory.collectAsState()
    val transferType by createRecordViewModel.transactionType.collectAsState()
    val scope = rememberCoroutineScope()
    val types by remember { mutableStateOf(listOf("INCOME", "EXPENSE")) }
    var selectedType by remember { mutableStateOf(types[0]) }
    val result by createRecordViewModel.result.collectAsState()
    val symbol by createRecordViewModel.symbol.collectAsState()
    var textNotes by remember { mutableStateOf("") }
    var textAmountSecond by remember { mutableStateOf("") }

    val selectedDate by createRecordViewModel.selectedDate.collectAsState()
    val selectedDateMills by createRecordViewModel.selectedDateMills.collectAsState()

    val selectedTime by createRecordViewModel.selectedTime.collectAsState()
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
            IconButton(onClick = { /*TODO*/ },
                modifier = Modifier.weight(1f),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Cancel",
                        tint = SecondaryColor
                    )
                    Text(text = "CANCEL", color = SecondaryColor)
                }
            }

            IconButton(onClick = {
                recordsViewModel.addNewRecord(
                    Record(
                        transactionType = if(selectedType == "INCOME") TransactionType.INCOME else TransactionType.EXPANSE,
                        categoryId = selectedCategory.id,
                        accountId = selectedAccount.id,
                        date = selectedDateMills,
                        time = selectedTime,
                        amount = result.toDouble(),
                        notes = textNotes
                    )
                )
                mainNavController.popBackStack()
            }, modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = SecondaryColor
                    )
                    Text(text = "SAVE", color = SecondaryColor)
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
                            checkmarkColor = MainColor,
                            uncheckedColor = MainColor,
                            checkedColor = SecondaryColor,
                        ),
                        checked = (selectedType == type),
                        onCheckedChange = {
                            //selectedType = if(it) type else ""
                        }
                    )
                    Text(text = type,
                        fontSize = 14.sp,
                        color = SecondaryColor,
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
                Text(text = recordFrom, color = SecondaryColor, fontSize = 14.sp, fontFamily = PBGFont, fontWeight = FontWeight.Normal, modifier = Modifier.padding(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
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
                        painter = painterResource(id = selectedAccount.icon),
                        colorFilter = if(selectedAccount.icon == R.drawable.walletbig)
                            ColorFilter.tint(SecondaryColor) else ColorFilter.tint(Color.Transparent, BlendMode.Color),
                        contentDescription = selectedAccount.name,
                        modifier = Modifier
                            .size(35.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                    )
                    Text(text = selectedAccount.name, color = SecondaryColor)
                }

            }
            Spacer(modifier = Modifier.width(2.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = recordTo, fontSize = 14.sp, modifier = Modifier.padding(4.dp), color = SecondaryColor)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(5.dp))
                        .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
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
                        painter = painterResource(id = selectedCategory.icon),
                        contentDescription = "Category",
                        colorFilter = if(selectedCategory.icon == R.drawable.price)
                            ColorFilter.tint(SecondaryColor) else ColorFilter.tint(Color.Transparent, BlendMode.Color),
                        modifier = Modifier
                            .size(35.dp)
                            .clip(CircleShape)
                            .border(2.dp, Color.White, CircleShape)
                    )
                    Text(text = selectedCategory.title, color = SecondaryColor)
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
                        .background(SecondaryColor, RoundedCornerShape(5.dp)),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = SharpMainColor,
                        unfocusedContainerColor = SharpMainColor
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
                    .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp))
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
                        tint = SecondaryColor
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
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "+", fontSize = 30.sp, color = MainColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('7') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "7", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('8') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "8", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('9') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "9", fontSize = 30.sp, color = SecondaryColor)
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
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "-", fontSize = 30.sp, color = MainColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('4') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "4", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('5') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "5", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('6') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),

                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "6", fontSize = 30.sp, color = SecondaryColor)
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
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "*", fontSize = 30.sp, color = MainColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('1') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "1", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('2') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "2", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('3') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "3", fontSize = 30.sp, color = SecondaryColor)
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
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "/", fontSize = 30.sp, color = MainColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('0') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = "0", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('.') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = MainColor,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .border(1.dp, SecondaryColor, RoundedCornerShape(4.dp)),
                ) {
                    Text(text = ".", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { createRecordViewModel.processCalculation('=') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "=", fontSize = 30.sp, color = MainColor)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { createRecordViewModel.setDatePicker(true) }, modifier = Modifier.weight(1f)) {
                    Text(text = selectedDate, fontSize = 20.sp, color = SecondaryColor)
                }
                TextButton(onClick = { createRecordViewModel.setTimePicker(true) }, modifier = Modifier.weight(1f)) {
                    Text(text = selectedTime, fontSize = 20.sp, color = SecondaryColor)
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