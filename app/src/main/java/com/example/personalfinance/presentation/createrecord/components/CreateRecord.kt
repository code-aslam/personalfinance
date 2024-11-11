package com.example.personalfinance.presentation.createrecord.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.ui.ListItemAccount
import com.example.personalfinance.ui.ListItemCategory
import com.example.personalfinance.ui.ListItemCreateRecordAccount
import com.example.personalfinance.ui.ListItemHeaderAccount
import com.example.personalfinance.ui.theme.AccentColor
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.LightBeige
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.PBGFont
import com.example.personalfinance.ui.theme.SecondaryColor
import kotlinx.coroutines.launch

@Composable
fun CreateRecordScreen(accountViewModel: AccountViewModel,
                       categoryViewModel: CategoryViewModel,
                       mainNavController: NavHostController){
    Scaffold(modifier = Modifier
        .fillMaxSize()
        .background(Beige)) { innerPadding ->
        CreateRecord(accountViewModel,recordsViewModel = hiltViewModel(),categoryViewModel,innerPadding, mainNavController)
    }
}

@Composable
fun CreateRecord(accountViewModel: AccountViewModel, recordsViewModel: RecordsViewModel, categoryViewModel: CategoryViewModel, paddingValues: PaddingValues, mainNavController: NavHostController) {
    val accounts by accountViewModel.accountList.collectAsState()
    val categoriesIncome by categoryViewModel.incomeCategoryList.collectAsState()
    val categoriesExpanse by categoryViewModel.expanseCategoryList.collectAsState()
    val bottomSheetStateAccount = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val bottomSheetStateCategory = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
    val scope = rememberCoroutineScope()
    val types by remember {
        mutableStateOf(listOf("INCOME", "EXPENSE", "TRANSFER"))
    }
    var selectedType by remember {
        mutableStateOf(types[0])
    }
    val result by recordsViewModel.result.collectAsState()
    val symbol by recordsViewModel.symbol.collectAsState()
    var textNotes by remember { mutableStateOf("") }
    var textAmountSecond by remember { mutableStateOf("") }
    var recordFrom by remember {
        mutableStateOf("Account")
    }
    recordFrom = when(selectedType){
        "INCOME", "EXPENSE" -> "Account"
        else -> "From"
    }
    var recordTo by remember {
        mutableStateOf("Category")
    }
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
            modifier = Modifier.padding(10.dp)
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
                    Record(notes = textNotes)
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
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            types.forEach {
                    type -> Checkbox(
                colors = CheckboxDefaults.colors(
                    checkmarkColor = AccentColor,
                    uncheckedColor = SecondaryColor,
                    checkedColor = SecondaryColor,
                ), checked = (selectedType == type), onCheckedChange = {
                selectedType = if(it) type else ""
            })
                Text(text = type, fontSize = 12.sp, color = SecondaryColor)
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
                IconButton(onClick = { scope.launch { bottomSheetStateAccount.show()
                    bottomSheetStateCategory.hide()} },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = SecondaryColor, shape = RoundedCornerShape(5.dp)))
                {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.account),
                            contentDescription = "Account",
                            tint = AccentColor
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "Account")
                    }
                }

            }
            Spacer(modifier = Modifier.width(2.dp))
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = recordTo, fontSize = 14.sp, modifier = Modifier.padding(4.dp), color = SecondaryColor)
                IconButton(onClick = { scope.launch { bottomSheetStateCategory.show()
                    bottomSheetStateAccount.hide()} },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = SecondaryColor, shape = RoundedCornerShape(5.dp)))
                {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.card),
                            contentDescription = "Card",
                            tint = AccentColor
                        )
                        Text(text = "Card")
                    }
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
                    .background(
                        color = SecondaryColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ){
                TextField(
                    value = textNotes,
                    onValueChange = { textNotes = it },
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = SecondaryColor,
                        unfocusedContainerColor = SecondaryColor
                    ),
                )
            }

            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
                modifier = Modifier
                    .background(
                        color = SecondaryColor,
                        shape = RoundedCornerShape(4.dp)
                    )
                    .weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = symbol, fontSize = 30.sp, modifier = Modifier.weight(2f))
                Text(
                    text = result,
                    modifier = Modifier
                        .weight(8f),
                    textAlign = TextAlign.End,
                    fontSize = 40.sp
                )
                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(2f)) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Clear amount one at a time",
                        tint = Color.Black
                    )
                }

            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { recordsViewModel.processCalculation('+') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = AccentColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "+", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('7') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "7", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('8') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "8", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('9') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "9", fontSize = 30.sp)
                }
            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { recordsViewModel.processCalculation('-') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = AccentColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "-", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('4') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "4", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('5') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "5", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('6') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "6", fontSize = 30.sp)
                }
            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { recordsViewModel.processCalculation('*') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = AccentColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "*", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('1') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "1", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('2') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "2", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('3') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "3", fontSize = 30.sp)
                }
            }
            Spacer(modifier = Modifier
                .height(2.dp)
                .fillMaxWidth())
            Row(
            ) {
                // + 7 8 9
                TextButton(onClick = { recordsViewModel.processCalculation('/') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = AccentColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "/", fontSize = 30.sp, color = SecondaryColor)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('0') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "0", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('.') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = SecondaryColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = ".", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('=') },
                    modifier = Modifier
                        .weight(1f)
                        .background(
                            color = AccentColor,
                            shape = RoundedCornerShape(4.dp)
                        ),
                ) {
                    Text(text = "=", fontSize = 30.sp, color = SecondaryColor)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Text(text = "Oct 30, 2024", fontSize = 20.sp, color = SecondaryColor)
                }
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Text(text = "11:00 AM", fontSize = 20.sp, color = SecondaryColor)
                }
            }
        }
    }

    ModalBottomSheetLayout(
        modifier = Modifier.padding(paddingValues),
        sheetState = bottomSheetStateAccount,
        sheetContent = {
            BottomSheetContentAccount(accounts,paddingValues,onDismiss = {
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
                "INCOME" -> BottomSheetContentCategory(categoriesIncome,paddingValues,onDismiss = {
                    scope.launch { bottomSheetStateCategory.hide() }
                })
                "EXPENSE" -> BottomSheetContentCategory(categoriesExpanse,paddingValues,onDismiss = {
                    scope.launch { bottomSheetStateCategory.hide() }
                })
                "TRANSFER" -> BottomSheetContentAccount(accounts,paddingValues,onDismiss = {
                    scope.launch { bottomSheetStateAccount.hide() }
                })
            }
        }
    ) {
        // Additional content if needed
    }
}

@Composable
fun BottomSheetContentAccount(accounts : List<Account>, paddingValues: PaddingValues, onDismiss: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Beige)
    ) {
        item {
            ListItemHeaderAccount(title = "Select an account")
        }


        items(accounts.size) { index ->
            ListItemCreateRecordAccount(
                iconWidth = DpSize(30.dp, 30.dp),
                account = accounts[index],
                onItemClick = {}
            )
        }
    }
}

@Composable
fun BottomSheetContentCategory(categories : List<Category>, paddingValues: PaddingValues, onDismiss: () -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .background(Beige)
    ) {
        item {
            ListItemHeaderAccount(title = "Select an account")
        }


        items(categories.size) { index ->
            ListItemCategory(
                iconWidth = DpSize(30.dp, 30.dp),
                category = categories[index],
                menuAction = {},
                onItemClick = {}
            )
        }
    }
}