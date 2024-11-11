package com.example.personalfinance.presentation.budgets.components

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
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.ui.Toolbar
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.BottomShadow
import com.example.personalfinance.ui.theme.CharcoalGrey
import com.example.personalfinance.ui.theme.DeepBurgundy
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Budgets(
    padding: PaddingValues,
    handleDrawer : () -> Unit){
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)) {
        item{
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            BudgetHeader(padding = padding)
            BottomShadow()
        }
        item {
            Text(text = "Budgets", fontSize = 20.sp)
        }
    }
}

@Composable
fun BudgetHeader(padding: PaddingValues){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        elevation = 0.dp,
        backgroundColor = MainColor
    ) {
        Column(
            modifier = Modifier.fillMaxHeight().fillMaxWidth()
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