package com.example.personalfinance.presentation.categories.components

import android.content.SharedPreferences.Editor
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.Card
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.presentation.HomeViewModel
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.ui.Toolbar
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.BottomShadow
import com.example.personalfinance.ui.ListItemCategory
import com.example.personalfinance.ui.ListItemHeader
import com.example.personalfinance.ui.theme.CharcoalGrey
import com.example.personalfinance.ui.theme.DeepBurgundy

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    padding: PaddingValues,
    handleDrawer: () -> Unit,
    viewModel: CategoryViewModel
) {
    val incomeCategories by viewModel.incomeCategoryList.collectAsState()
    val expanseCategories by viewModel.expanseCategoryList.collectAsState()

    var selectedCategory by remember {
        mutableStateOf(
            Category(
                title = "",
                icon = 0,
                type = CategoryType.INCOME
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
            .background(Beige)
    ) {
        item {
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            CategoryHeader(padding = padding)
            BottomShadow()
        }
        item {
            ListItemHeader(title = "Income Categories")
        }

        items(incomeCategories.size) { index ->
            ListItemCategory(
                iconRes = incomeCategories[index].icon,
                iconWidth = DpSize(30.dp, 30.dp),
                title = incomeCategories[index].title
            ) {
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
            }
        }

        item {
            ListItemHeader(title = "Expense Categories")
        }

        items(expanseCategories.size) { index ->
            ListItemCategory(
                iconRes = expanseCategories[index].icon,
                iconWidth = DpSize(30.dp, 30.dp),
                title = expanseCategories[index].title
            ) {
                selectedIndex = index
                selectedCategory = expanseCategories[index]
                when (it) {
                    "Edit" -> {viewModel.showEditAction()}
                    "Delete" -> {viewModel.showDeleteAction()}
                }
            }
        }

        item {
            Button(
                onClick = {
                    viewModel.showAddAction()
            }) {
                Text(text = "Add New Category")
            }
        }
    }

    DeleteDialog(viewModel = viewModel, selectedCategory = selectedCategory)
    EditDialog(viewModel = viewModel, selectedCategory = selectedCategory, selectedIndex = selectedIndex)
    AddDialog(viewModel = viewModel)
}


@Composable
fun DeleteDialog(viewModel: CategoryViewModel, selectedCategory: Category) {
    val showDelete by viewModel.showDelete.collectAsState()
    if (showDelete) {
        AlertDialog(
            containerColor = Beige,
            modifier = Modifier.background(Beige),
            onDismissRequest = { viewModel.hideDeleteAction() },
            title = {
                Text(
                    "Delete This Category",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            },
            text = {
                Text("Deleting this category will also delete all records and budgets for this category. Ary you sure ?")

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
                        viewModel.removeCategoryAction(selectedCategory)
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
fun EditDialog(viewModel: CategoryViewModel, selectedCategory: Category, selectedIndex :Int){
    val showEdit by viewModel.showEdit.collectAsState()
    if (showEdit) {
        var textValue by remember { mutableStateOf(selectedCategory.title) }
        AlertDialog(
            containerColor = Beige,
            modifier = Modifier.background(Beige),
            onDismissRequest = { viewModel.hideEditAction() },
            title = {
                Text(
                    "Edit category",
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
                        viewModel.updateCategoryAction(selectedCategory.copy(title = textValue), selectedIndex)
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
fun AddDialog(viewModel: CategoryViewModel){
    val showAdd by viewModel.showAdd.collectAsState()
    if (showAdd) {
        var textValue by remember { mutableStateOf("") }
        val types by remember {
            mutableStateOf(listOf("INCOME", "EXPENSE"))
        }
        var selectedType by remember {
            mutableStateOf(types[0])
        }
        AlertDialog(
            containerColor = Beige,
            modifier = Modifier.background(Beige),
            onDismissRequest = { viewModel.hideAddAction() },
            title = {
                Text(
                    "Add new category",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp
                )
            },
            text = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Type")
                        Spacer(modifier = Modifier.width(4.dp))
                        types.forEach {
                            type -> Checkbox(checked = (selectedType == type), onCheckedChange = {
                                selectedType = if(it) type else ""
                            })
                            Text(text = type)
                            Spacer(modifier = Modifier.width(2.dp))
                        }

                    }
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
                        viewModel.addNewCategoryAction(
                            Category(
                                title = textValue,
                                icon = R.drawable.salary,
                                type = when(selectedType){
                                    CategoryType.INCOME.name -> CategoryType.INCOME
                                    else -> CategoryType.EXPENSE
                                }
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

@Composable
fun CategoryHeader(padding: PaddingValues) {
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material.Text(text = "EXPANSE SO FAR")
                    androidx.compose.material.Text("1500.00", color = DeepBurgundy)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    androidx.compose.material.Text(text = "INCOME SO FAR")
                    androidx.compose.material.Text(text = "1200.00", color = CharcoalGrey)
                }
            }
        }


    }
}