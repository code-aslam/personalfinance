package com.example.personalfinance.presentation.accounts.components

import android.util.Log
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.example.personalfinance.ui.theme.DarkForestGreenColor
import com.example.personalfinance.ui.theme.DeepBurgundy
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SharpMainColor

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
            .background(MainColor)) {
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
        backgroundColor = MainColor
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
                    Text(text = "TOTAL SPENT", color = SecondaryColor)
                    Text("1500.00",  color = SecondaryColor)
                }
                Column(modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    Text(text = "TOTAL BUDGET", color = SecondaryColor)
                    Text(text = "1200.00",  color = SecondaryColor)
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
            containerColor = MainColor,
            modifier = Modifier.background(Color.Transparent),
            onDismissRequest = { viewModel.hideDeleteAction() },
            title = {
                Text(
                    "Delete This Account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = DarkForestGreenColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("Deleting this account will also delete all records with this account. Are you sure ?", color = DarkForestGreenColor, fontSize = 18.sp)
            },
            confirmButton = {
                Button(
                    modifier = Modifier
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor, // Set the background color
                        contentColor = DarkForestGreenColor // Set the text color
                    ),
                    onClick = {
                        viewModel.hideDeleteAction()
                        viewModel.deleteAccountAction(selectedAccount)
                    }) {
                    Text("YES")
                }

            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor, // Set the background color
                        contentColor = DarkForestGreenColor // Set the text color
                    ),
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
    val accountIconList by viewModel.accountIconList.collectAsState()

    if (showEdit) {
        var textNameValue by remember { mutableStateOf(selectedAccount.name) }
        var textInitialAmountValue by remember { mutableIntStateOf(selectedAccount.initialAmount) }
        var selectedIcon by remember {
            mutableIntStateOf(selectedAccount.icon)
        }
        AlertDialog(
            containerColor = MainColor,
            modifier = Modifier.background(Color.Transparent),
            onDismissRequest = { viewModel.hideEditAction() },
            title = {
                Text(
                    "Edit account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = DarkForestGreenColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Initial Amount", modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            value = textInitialAmountValue.toString(),
                            onValueChange = { textInitialAmountValue = it.toInt() },
                            modifier = Modifier
                                .weight(1.5f)
                                .background(DarkForestGreenColor, RoundedCornerShape(5.dp)),
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
                        Text(text = "Name", modifier = Modifier.weight(1f),color = DarkForestGreenColor)
                        OutlinedTextField(
                            value = textNameValue,
                            onValueChange = { textNameValue = it },
                            modifier = Modifier
                                .weight(5f)
                                .background(DarkForestGreenColor, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SharpMainColor,
                                unfocusedContainerColor = SharpMainColor
                            ),

                            )
                    }

                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Icon", color = DarkForestGreenColor)
                        Box(
                            modifier = Modifier
                                .background(SharpMainColor, RoundedCornerShape(5.dp))
                                .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp))
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
                                                    .size(if(selectedIcon == item) 52.dp else 50.dp)
                                                    .clip(RoundedCornerShape(5.dp))
                                                    .border(2.dp, if(selectedIcon == item) DarkForestGreenColor else Color.White, RoundedCornerShape(5.dp))
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
            confirmButton = {
                Button(
                    modifier = Modifier
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor, // Set the background color
                        contentColor = DarkForestGreenColor // Set the text color
                    ),
                    onClick = {
                        viewModel.hideEditAction()
                        viewModel.updateAccountAction(selectedAccount.copy(name = textNameValue, icon = selectedIcon, initialAmount = textInitialAmountValue), selectedIndex)
                    }) {
                    Text("SAVE")
                }

            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor, // Set the background color
                        contentColor = DarkForestGreenColor // Set the text color
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
    val accountIconList by viewModel.accountIconList.collectAsState()
    var selectedIcon by remember {
        mutableIntStateOf(accountIconList[0])
    }
    if (showAdd) {
        var textInitialAmountValue by remember { mutableStateOf("") }
        var textNameValue by remember { mutableStateOf("") }
        AlertDialog(
            containerColor = MainColor,
            modifier = Modifier.background(Color.Transparent),
            onDismissRequest = { viewModel.hideAddAction() },
            title = {
                Text(
                    "Add new account",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    color = DarkForestGreenColor,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Initial Amount", modifier = Modifier.weight(1f))
                        OutlinedTextField(
                            value = textInitialAmountValue,
                            onValueChange = { textInitialAmountValue = it },
                            modifier = Modifier
                                .weight(1.5f)
                                .background(DarkForestGreenColor, RoundedCornerShape(5.dp)),
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
                        Text(text = "Name", modifier = Modifier.weight(1f),color = DarkForestGreenColor)
                        OutlinedTextField(
                            value = textNameValue,
                            onValueChange = { textNameValue = it },
                            modifier = Modifier
                                .weight(5f)
                                .background(DarkForestGreenColor, RoundedCornerShape(5.dp)),
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = SharpMainColor,
                                unfocusedContainerColor = SharpMainColor
                            ),

                            )
                    }

                    Column(
                        modifier = Modifier.padding(top = 10.dp)
                    ) {
                        Text(text = "Icon", color = DarkForestGreenColor)
                        Box(
                            modifier = Modifier
                                .background(SharpMainColor, RoundedCornerShape(5.dp))
                                .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp))
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
                                                    .size(if(selectedIcon == item) 52.dp else 50.dp)
                                                    .clip(RoundedCornerShape(5.dp))
                                                    .border(2.dp, if(selectedIcon == item) DarkForestGreenColor else Color.White, RoundedCornerShape(5.dp))
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
            confirmButton = {
                Button(
                    modifier = Modifier
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor, // Set the background color
                        contentColor = DarkForestGreenColor // Set the text color
                    ),
                    onClick = {
                        viewModel.hideAddAction()
                        viewModel.addNewAccountAction(
                            Account(
                                name = textNameValue,
                                icon = selectedIcon,
                                initialAmount = textInitialAmountValue.toInt()
                            )
                        )
                    }) {
                    Text("SAVE", modifier = Modifier.background(MainColor))
                }

            },
            dismissButton = {
                Button(
                    modifier = Modifier
                        .background(MainColor, RoundedCornerShape(5.dp))
                        .border(1.dp, DarkForestGreenColor, RoundedCornerShape(5.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MainColor, // Set the background color
                        contentColor = DarkForestGreenColor // Set the text color
                    ),
                    shape = RectangleShape,
                    onClick = { viewModel.hideAddAction() }) {
                    Text("CANCEL", modifier = Modifier.background(MainColor))
                }
            }
        )
    }
}