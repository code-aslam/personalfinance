package com.example.personalfinance.presentation.categories.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.R
import com.example.personalfinance.presentation.HomeViewModel
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
    viewModel: HomeViewModel
) {
    val incomeCategories = viewModel.incomeCategoryList.collectAsState().value
    val expanseCategories = viewModel.expanseCategoryList.collectAsState().value
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

        items(incomeCategories) { item ->
            ListItemCategory(
                iconRes = item.icon,
                iconWidth = DpSize(30.dp, 30.dp),
                title = item.title
            )
        }

        item {
            ListItemHeader(title = "Expense Categories")
        }

        items(expanseCategories) { item ->
            ListItemCategory(
                iconRes = item.icon,
                iconWidth = DpSize(30.dp, 30.dp),
                title = item.title
            )
        }

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