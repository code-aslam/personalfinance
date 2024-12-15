package com.example.personalfinance.presentation.categories.components

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
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.presentation.categories.CategoryViewModel
import com.example.personalfinance.presentation.ui.components.Dialog
import com.example.personalfinance.ui.BottomShadow
import com.example.personalfinance.ui.ListItemCategory
import com.example.personalfinance.ui.ListItemHeader
import com.example.personalfinance.ui.Toolbar
import com.example.personalfinance.ui.theme.DarkForestGreenColor
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SharpMainColor
import com.example.personalfinance.ui.theme.SoftPinkColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    padding: PaddingValues,
    handleDrawer: () -> Unit,
    viewModel: CategoryViewModel
) {

    LaunchedEffect(Unit){
        viewModel.fetchCategories()
    }

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
            CategoryHeader(padding = padding)
            BottomShadow()
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
                        .clickable { viewModel.showAddAction() }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add new category",
                        tint = SecondaryColor
                    )
                    Text(text = "Add New Category", color = SecondaryColor)
                }
            }


        }
    }

    if(showDelete) {
        Dialog(
            title = "Delete This Category",
            content = {
                Text("Deleting this category will also delete all records and budgets for this category. Are you sure ?", color = SecondaryColor, fontSize = 18.sp)
            },
            confirmText = "YES",
            onConfirm = { viewModel.removeCategoryAction(selectedCategory)},
            dismissText = "NO",
            onDismiss = { viewModel.hideDeleteAction() }
        )
    }

    if(showEdit){
        var selectedIcon by remember { mutableIntStateOf(selectedCategory.icon) }
        var textValue by remember { mutableStateOf(selectedCategory.title) }

        Dialog(
            title = "Edit Category",
            content = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Name", modifier = Modifier.weight(1f),color = SecondaryColor)
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = { textValue = it },
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
                                items(categoryIconList.chunked(2)) { rowItems ->
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
            mutableStateOf(listOf("INCOME", "EXPENSE"))
        }
        var selectedType by remember {
            mutableStateOf(types[0])
        }
        var selectedIcon by remember {
            mutableIntStateOf(categoryIconList[0])
        }
        Dialog(
            title = "Add new category",
            content = {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(text = "Type", color = SecondaryColor)
                        Spacer(modifier = Modifier.width(4.dp))
                        types.forEach {
                                type -> Checkbox(checked = (selectedType == type), onCheckedChange = {
                            selectedType = if(it) type else ""
                        })
                            Text(text = type, color = SecondaryColor)
                            Spacer(modifier = Modifier.width(2.dp))
                        }

                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Name", modifier = Modifier.weight(1f),color = SecondaryColor)
                        OutlinedTextField(
                            value = textValue,
                            onValueChange = { textValue = it },
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
                                items(categoryIconList.chunked(2)) { rowItems ->
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
            confirmText = "SAVE",
            onConfirm = { viewModel.addNewCategoryAction(
                Category(
                    title = textValue,
                    icon = selectedIcon,
                    type = when(selectedType){
                        CategoryType.INCOME.name -> CategoryType.INCOME
                        else -> CategoryType.EXPENSE
                    }
                )
            )},
            dismissText = "CANCEL",
            onDismiss = { viewModel.hideAddAction()  }
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
        backgroundColor = MainColor
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
                    Text(text = "EXPANSE SO FAR", color = SecondaryColor)
                    Text("1500.00", color = SoftPinkColor)
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "INCOME SO FAR", color = SecondaryColor)
                    Text(text = "1200.00", color = DarkForestGreenColor)
                }
            }
        }


    }
}