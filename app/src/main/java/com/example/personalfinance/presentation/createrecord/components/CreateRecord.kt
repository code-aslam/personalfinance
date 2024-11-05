package com.example.personalfinance.presentation.createrecord.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.ui.ListItemAccount
import com.example.personalfinance.ui.ListItemHeaderAccount
import com.example.personalfinance.ui.theme.Beige
import kotlinx.coroutines.launch

@Composable
fun CreateRecordScreen(accountViewModel: AccountViewModel, recordsViewModel: RecordsViewModel){
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        CreateRecord(accountViewModel,recordsViewModel,innerPadding)
    }
}

@Composable
fun CreateRecord(accountViewModel: AccountViewModel, recordsViewModel: RecordsViewModel, paddingValues: PaddingValues) {

    val accounts by accountViewModel.accountList.collectAsState()
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
    Log.e("aslam134", accounts.size.toString())
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 10.dp,
                end = 10.dp,
                bottom = paddingValues.calculateBottomPadding()
            )
    ) {
        // Cancel and save button
        Row(
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
                        tint = Color.Black
                    )
                    Text(text = "CANCEL")
                }
            }

            IconButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Save",
                        tint = Color.Black
                    )
                    Text(text = "SAVE")
                }
            }
        }

        // INCOME, EXPANSE, TRANSFER Transaction type
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            types.forEach {
                    type -> Checkbox(checked = (selectedType == type), onCheckedChange = {
                selectedType = if(it) type else ""
            })
                Text(text = type, fontSize = 12.sp)
            }
        }

        // Account and Category chooser (Bottom sheet with lazy column )
        Row (
        ){
            Column(
                modifier = Modifier
                    .weight(1f) ,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "From", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
                IconButton(onClick = { scope.launch {
                    bottomSheetStateAccount.show()
                    bottomSheetStateCategory.hide()}
                },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(2.dp))
                        .border(2.dp, Color.Black)) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Account",
                            tint = Color.Black
                        )
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
                Text(text = "To", fontSize = 14.sp, modifier = Modifier.padding(4.dp))
                IconButton(onClick = { scope.launch { bottomSheetStateCategory.show()
                    bottomSheetStateAccount.hide()} },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(2.dp))
                        .border(2.dp, Color.Black)) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = "Card",
                            tint = Color.Black
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
        ) {
            TextField(
                value = textNotes,
                onValueChange = { textNotes = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(2.dp))
                    .border(2.dp, Color.Black)
                    .weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Beige,
                    unfocusedContainerColor = Beige
                ),
                shape = RectangleShape
            )
            Spacer(modifier = Modifier
                .height(4.dp)
                .fillMaxWidth())
            Row(
                modifier = Modifier
                    .background(Beige)
                    .clip(RoundedCornerShape(2.dp))
                    .border(2.dp, Color.Black)
                    .weight(0.5f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = symbol, fontSize = 30.sp, modifier = Modifier.weight(2f))
                Text(
                    text = result,
                    modifier = Modifier
                        .weight(8f),
                    textAlign = TextAlign.End,
                    fontSize = 25.sp
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
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "+", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('7') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "7", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('8') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "8", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('9') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
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
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "-", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('4') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "4", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('5') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "5", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('6') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
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
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "*", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('1') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "1", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('2') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "2", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('3') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
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
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "/", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('0') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "0", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('.') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = ".", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.width(1.dp))
                TextButton(onClick = { recordsViewModel.processCalculation('=') },
                    modifier = Modifier
                        .weight(1f)
                        .border(1.dp, Color.Black),
                    shape = RoundedCornerShape(1.dp)
                ) {
                    Text(text = "=", fontSize = 30.sp)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Text(text = "Oct 30, 2024", fontSize = 20.sp)
                }
                TextButton(onClick = { /*TODO*/ }, modifier = Modifier.weight(1f)) {
                    Text(text = "11:00 AM", fontSize = 20.sp)
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
            BottomSheetContentCategory(onDismiss = {
                scope.launch { bottomSheetStateCategory.hide() }
            })
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
            ListItemAccount(
                iconWidth = DpSize(30.dp, 30.dp),
                account = accounts[index]
            ) {

            }
        }
    }
}

@Composable
fun BottomSheetContentCategory(onDismiss: () -> Unit) {
    Column {
        //Text("This is a bottom sheet Category")
    }
}