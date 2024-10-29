package com.example.personalfinance.presentation.accounts.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.material.Card
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.ui.Toolbar
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.BottomShadow
import com.example.personalfinance.ui.ListItemAccount
import com.example.personalfinance.ui.ListItemCategory
import com.example.personalfinance.ui.ListItemHeaderAccount
import com.example.personalfinance.ui.theme.CharcoalGrey
import com.example.personalfinance.ui.theme.DeepBurgundy

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Accounts(
    padding: PaddingValues,
    handleDrawer : () -> Unit,
    accountViewModel: AccountViewModel){
    
    val accountList by accountViewModel.accountList.collectAsState()

    var selectedAccount by remember {
        mutableStateOf(
            Account(
                name = "Saving",
                icon = R.drawable.salary
            )
        )
    }

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Beige)) {
        item{
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            AccountHeader(padding = padding)
            BottomShadow()
        }
        item {
            ListItemHeaderAccount(title = "Accounts")
        }


        items(accountList.size) { index ->
            ListItemAccount(
                iconWidth = DpSize(30.dp, 30.dp),
                account = accountList[index]
            ) {
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
            }
        }

        item {
            Button(
                onClick = {
                    accountViewModel.showAddAction()
                }) {
                Text(text = "Add New Account")
            }
        }
    }

    DeleteDialog(viewModel = accountViewModel, selectedAccount = selectedAccount)
    EditDialog(viewModel = accountViewModel, selectedAccount = selectedAccount, selectedIndex = selectedIndex)
    AddDialog(viewModel = accountViewModel)
}

@Composable
fun AccountHeader(padding: PaddingValues){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        elevation = 0.dp,
        backgroundColor = Color(245, 222, 179)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    androidx.compose.material.Text(text = "TOTAL SPENT")
                    androidx.compose.material.Text("1500.00",  color = DeepBurgundy)
                }
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    androidx.compose.material.Text(text = "TOTAL BUDGET")
                    androidx.compose.material.Text(text = "1200.00",  color = CharcoalGrey)
                }
            }
        }
    }
}

@Composable
fun DeleteDialog(viewModel: AccountViewModel, selectedAccount: Account) {
    val showDelete by viewModel.showDelete.collectAsState()
    if (showDelete) {
        AlertDialog(
            containerColor = Beige,
            modifier = Modifier.background(Beige),
            onDismissRequest = { viewModel.hideDeleteAction() },
            title = {
                Text(
                    "Delete This Account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            },
            text = {
                Text("Deleting this account will also delete all records with this account. Ary you sure ?")

            },
            confirmButton = {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Beige, // Set the background color
                        contentColor = CharcoalGrey // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = {
                        viewModel.hideDeleteAction()
                        viewModel.deleteAccountAction(selectedAccount)
                    }) {
                    Text("YES", modifier = Modifier.background(Beige))
                }

            },
            dismissButton = {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Beige, // Set the background color
                        contentColor = CharcoalGrey // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = { viewModel.hideDeleteAction() }) {
                    Text("NO")
                }
            }
        )
    }
}

@Composable
fun EditDialog(viewModel: AccountViewModel, selectedAccount: Account, selectedIndex :Int){
    val showEdit by viewModel.showEdit.collectAsState()
    if (showEdit) {
        var textValue by remember { mutableStateOf(selectedAccount.name) }
        AlertDialog(
            containerColor = Beige,
            modifier = Modifier.background(Beige),
            onDismissRequest = { viewModel.hideEditAction() },
            title = {
                Text(
                    "Edit account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Name", modifier = Modifier.weight(1f))
                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        modifier = Modifier.weight(5f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Beige,
                            unfocusedContainerColor = Beige
                        ),
                        shape = RectangleShape
                    )
                }
            },
            confirmButton = {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Beige, // Set the background color
                        contentColor = CharcoalGrey // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = {
                        viewModel.hideEditAction()
                        viewModel.updateAccountAction(selectedAccount.copy(name = textValue), selectedIndex)
                    }) {
                    Text("SAVE", modifier = Modifier.background(Beige))
                }

            },
            dismissButton = {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Beige, // Set the background color
                        contentColor = CharcoalGrey // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = { viewModel.hideEditAction() }) {
                    Text("CANCEL")
                }
            }
        )
    }
}


@Composable
fun AddDialog(viewModel: AccountViewModel){
    val showAdd by viewModel.showAdd.collectAsState()
    if (showAdd) {
        var textValue by remember { mutableStateOf("") }
        
        AlertDialog(
            containerColor = Beige,
            modifier = Modifier.background(Beige),
            onDismissRequest = { viewModel.hideAddAction() },
            title = {
                Text(
                    "Add new account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Name", modifier = Modifier.weight(1f))
                    OutlinedTextField(
                        value = textValue,
                        onValueChange = { textValue = it },
                        modifier = Modifier.weight(5f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Beige,
                            unfocusedContainerColor = Beige
                        ),
                        shape = RectangleShape
                    )
                }

            },
            confirmButton = {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Beige, // Set the background color
                        contentColor = CharcoalGrey // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = {
                        viewModel.hideAddAction()
                        viewModel.addNewAccountAction(
                            Account(
                                name = textValue,
                                icon = R.drawable.salary,
                            )
                        )
                    }) {
                    Text("SAVE", modifier = Modifier.background(Beige))
                }

            },
            dismissButton = {
                OutlinedButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Beige, // Set the background color
                        contentColor = CharcoalGrey // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = { viewModel.hideAddAction() }) {
                    Text("CANCEL")
                }
            }
        )
    }
}