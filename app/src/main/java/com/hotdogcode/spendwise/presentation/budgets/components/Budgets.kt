package com.hotdogcode.spendwise.presentation.budgets.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.presentation.ui.components.FinanceHeader
import com.hotdogcode.spendwise.ui.Toolbar

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Budgets(
    padding: PaddingValues,
    handleDrawer : () -> Unit){

    val dataMap: MutableMap<String, String> = mutableMapOf(
        "TOTAL SPENT" to "1500.00",
        "TOTAL BUDGET" to "1200.00"
    )

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
            FinanceHeader(dataMap)
        }
        item {
            Text(text = "Budgets", fontSize = 20.sp)
        }
    }
}