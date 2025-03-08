package com.hotdogcode.spendwise.presentation.analysis.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.presentation.analysis.AnalysisViewModel
import com.hotdogcode.spendwise.presentation.records.RecordsViewModel
import com.hotdogcode.spendwise.presentation.ui.components.FinanceHeader
import com.hotdogcode.spendwise.presentation.ui.components.HDCPieChart
import com.hotdogcode.spendwise.ui.ListItemHeaderAccount
import com.hotdogcode.spendwise.ui.Toolbar
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.red
import ir.ehsannarmani.compose_charts.PieChart
import ir.ehsannarmani.compose_charts.models.Pie

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Analysis(
    padding: PaddingValues,
    handleDrawer: () -> Unit,
    viewModel: AnalysisViewModel
) {
    val recordsViewModel : RecordsViewModel = hiltViewModel()
    val recordList by recordsViewModel.dateSortedRecords.collectAsState()
    var data by remember {
        mutableStateOf(
            listOf(
                Pie(label = "Android", data = 20.0, color = Color.Red),
                Pie(label = "Windows", data = 45.0, color = Color.Cyan),
                Pie(label = "Linux", data = 35.0, color = Color.Gray),
                Pie(label = "Andrd", data = 20.0, color = Color.Red),
                Pie(label = "Winows", data = 45.0, color = Color.Cyan),
                Pie(label = "Linx", data = 35.0, color = Color.Gray),
                Pie(label = "Andrid", data = 20.0, color = Color.Red),
                Pie(label = "Wndows", data = 45.0, color = Color.Cyan),
                Pie(label = "Liux", data = 35.0, color = Color.Gray),
            )
        )
    }

    var isCalculationCompleted by remember { mutableStateOf(false) }
    val dataMap = remember {
        mutableStateMapOf(
            "EXPENSE" to "calculating...",
            "INCOME" to "calculating...",
            "TOTAL" to "calculating...")
    }



    LaunchedEffect(recordList) {
        isCalculationCompleted = false
        var income = 0.0
        var expense = 0.0
        for (record in recordList.values) {
            income += record.filter { it.transactionType == TransactionType.INCOME }.sumOf { it.amount }
            expense += record.filter { it.transactionType == TransactionType.EXPANSE }.sumOf { it.amount }
        }
        dataMap["EXPENSE"] = expense.toString()
        dataMap["INCOME"] = income.toString()
        dataMap["TOTAL"] = (income - expense).toString()
        isCalculationCompleted = true
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .background(Color.White)
    ) {
        item {
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            FinanceHeader(dataMap, isCalculationCompleted)
        }
        item{
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
        }
        item {
            ListItemHeaderAccount(title = "Expense Analysis")
        }
        items(data.size) {index->
            Box(
                modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp),
                contentAlignment = Alignment.CenterStart
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth(fraction = data[index].data.toFloat() / 100f)
                        .height(40.dp)
                        .background(data[index].color),

                )
                Text("${data[index].label!!}: ${data[index].data}%")
            }

           Spacer(modifier = Modifier.fillMaxWidth().height(5.dp).background(Color.White))
        }
    }
}