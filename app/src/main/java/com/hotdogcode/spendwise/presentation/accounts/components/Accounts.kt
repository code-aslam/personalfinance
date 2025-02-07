package com.hotdogcode.spendwise.presentation.accounts.components

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.presentation.accounts.AccountViewModel
import com.hotdogcode.spendwise.presentation.ui.components.Dialog
import com.hotdogcode.spendwise.presentation.ui.components.FinanceHeader
import com.hotdogcode.spendwise.ui.ListItemAccount
import com.hotdogcode.spendwise.ui.ListItemHeaderAccount
import com.hotdogcode.spendwise.ui.Toolbar
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SharpMainColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Accounts(
    padding: PaddingValues,
    handleDrawer : () -> Unit,
    accountViewModel: AccountViewModel){
    var isCalculationCompleted by remember { mutableStateOf(false) }
    val recordList by accountViewModel.dataRecords.collectAsState()
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
            if(record.transactionType == TransactionType.EXPANSE)
                expense += record.amount
        }
        dataMap["INCOME SO FAR"] = income.toString()
        dataMap["EXPENSE SO FAR"] = expense.toString()
        isCalculationCompleted = true
    }
    
    val accountList by accountViewModel.accountList.collectAsState()

    var selectedAccount by remember {
        mutableStateOf(
            Account(
                name = "Saving",
                icon = R.drawable.salary
            )
        )
    }


    var selectedIndex by remember { mutableIntStateOf(0) }

    val showDelete by accountViewModel.showDelete.collectAsState()
    val showEdit by accountViewModel.showEdit.collectAsState()
    val accountIconList by accountViewModel.accountIconList.collectAsState()
    val showAdd by accountViewModel.showAdd.collectAsState()


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(MainColor)) {
        item{
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            FinanceHeader(dataMap, isCalculationCompleted)
        }
        item {
            ListItemHeaderAccount(title = "Accounts")
        }


        items(accountList.size) { index ->
            ListItemAccount(
                iconWidth = DpSize(45.dp, 45.dp),
                account = accountList[index],
                menuAction = {
                    selectedIndex = index
                    selectedAccount = accountList[index]
                    when (it) {
                        "Edit" -> {
                            accountViewModel.showEditAction()
                        }
                        "Delete" -> {
                            accountViewModel.showDeleteAction()
                        }
                    }
                },
                onItemClick = {
                    //
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
                        .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
                        .clickable { accountViewModel.showAddAction() }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new account",
                        tint = SecondaryColor
                    )
                    Text(text = "Add New Account", color = SecondaryColor)
                }
            }


        }
    }

    if(showDelete) {
        Dialog(
            title = "Delete This Account",
            content = {
                Text("Deleting this account will also delete all records with this account. Are you sure ?", color = SecondaryColor, fontSize = 18.sp)
            },
            confirmText = "YES",
            onConfirm = { accountViewModel.deleteAccountAction(selectedAccount)},
            dismissText = "NO",
            onDismiss = { accountViewModel.hideDeleteAction() }
        )
    }

    if(showEdit){
        var textNameValue by remember { mutableStateOf(selectedAccount.name) }
        var textInitialAmountValue by remember { mutableStateOf(selectedAccount.initialAmount.toString()) }
        var selectedIcon by remember { mutableIntStateOf(selectedAccount.icon) }
        Dialog(
            title = "Edit account",
            content = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Initial Amount", modifier = Modifier.weight(1f), color = SecondaryColor)
                        OutlinedTextField(
                            value = textInitialAmountValue,
                            onValueChange = {
                                textInitialAmountValue = it
                            },
                            modifier = Modifier
                                .weight(1.5f)
                                .background(SecondaryColor, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SharpMainColor,
                                unfocusedContainerColor = SharpMainColor
                            )
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Name", modifier = Modifier.weight(1f),color = SecondaryColor)
                        OutlinedTextField(
                            value = textNameValue,
                            onValueChange = { textNameValue = it },
                            modifier = Modifier
                                .weight(5f)
                                .background(SecondaryColor, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SharpMainColor,
                                unfocusedContainerColor = SharpMainColor
                            ),

                            )
                    }

                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Icon", color = SecondaryColor)
                        Box(
                            modifier = Modifier
                                .background(SharpMainColor, RoundedCornerShape(5.dp))
                                .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
                                .padding(10.dp)
                        ){
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(accountIconList.chunked(2)) { rowItems ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowItems.forEach { item ->
                                            Image(
                                                painter = painterResource(id = item),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(if (selectedIcon == item) 52.dp else 50.dp)
                                                    .clip(RoundedCornerShape(5.dp))
                                                    .border(
                                                        2.dp,
                                                        if (selectedIcon == item) DarkForestGreenColor else Color.White,
                                                        RoundedCornerShape(5.dp)
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
            onConfirm = {
                accountViewModel.updateAccountAction(
                    selectedAccount.copy(
                        name = textNameValue,
                        icon = selectedIcon,
                        initialAmount = if(textInitialAmountValue.toDoubleOrNull() != null) textInitialAmountValue.toDouble() else 0.0),
                    selectedIndex)
            },
            dismissText = "NO",
            onDismiss = { accountViewModel.hideEditAction() }
        )
    }

    if(showAdd) {
        var textNameValue by remember { mutableStateOf("") }
        var textInitialAmountValue by remember { mutableStateOf("") }
        var selectedIcon by remember { mutableIntStateOf(accountIconList[0]) }
        Dialog(
            title = "Add new account",
            content = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Initial Amount", modifier = Modifier.weight(1f), color = SecondaryColor)
                        OutlinedTextField(
                            value = textInitialAmountValue,
                            onValueChange = { textInitialAmountValue = it },
                            modifier = Modifier
                                .weight(1.5f)
                                .background(SecondaryColor, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SharpMainColor,
                                unfocusedContainerColor = SharpMainColor
                            )
                        )
                    }
                    Row(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Name", modifier = Modifier.weight(1f),color = SecondaryColor)
                        OutlinedTextField(
                            value = textNameValue,
                            onValueChange = { textNameValue = it },
                            modifier = Modifier
                                .weight(5f)
                                .background(SecondaryColor, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SharpMainColor,
                                unfocusedContainerColor = SharpMainColor
                            ),

                            )
                    }

                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Icon", color = SecondaryColor)
                        Box(
                            modifier = Modifier
                                .background(SharpMainColor, RoundedCornerShape(5.dp))
                                .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp))
                                .padding(10.dp)
                        ){
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                items(accountIconList.chunked(2)) { rowItems ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        rowItems.forEach { item ->
                                            Image(
                                                painter = painterResource(id = item),
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(if (selectedIcon == item) 52.dp else 50.dp)
                                                    .clip(RoundedCornerShape(5.dp))
                                                    .border(
                                                        2.dp,
                                                        if (selectedIcon == item) DarkForestGreenColor else Color.White,
                                                        RoundedCornerShape(5.dp)
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
            onConfirm = { accountViewModel.addNewAccountAction(Account(
                    name = textNameValue,
                    icon = selectedIcon,
                    initialAmount = textInitialAmountValue.toDouble()
                )
            )},
            dismissText = "CANCEL",
            onDismiss = { accountViewModel.hideAddAction() }
        )
    }
}

