package com.example.personalfinance.presentation.records.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.personalfinance.common.TransactionType
import com.example.personalfinance.common.toRequiredFormat
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.presentation.ui.components.FinanceHeader
import com.example.personalfinance.ui.ListItemRecord
import com.example.personalfinance.ui.Toolbar
import com.example.personalfinance.ui.theme.DarkForestGreenColor
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.OpaqueSecondaryColor
import com.example.personalfinance.ui.theme.SecondaryColor
import java.time.LocalDate


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Records(padding: PaddingValues,
            handleDrawer: () -> Unit,
            recordsViewModel: RecordsViewModel,
navController: NavHostController) {
    val recordList by recordsViewModel.dateSortedRecords.collectAsState()
    var selectedRecord by remember { mutableStateOf(RecordWithCategoryAndAccount()) }
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

    List(recordList,
        padding,
        handleDrawer,
        { FinanceHeader(dataMap, isCalculationCompleted)}
    ){
        record -> selectedRecord = record
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(
    recordList : Map<LocalDate, List<RecordWithCategoryAndAccount>>,
         padding: PaddingValues,
         handleDrawer: () -> Unit,
         header : @Composable () -> Unit,
         onItemClick : (RecordWithCategoryAndAccount) -> Unit){

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
            header()
        }

        recordList.forEach { (localDate, recordWithCategoryAndAccounts) ->
            item {
                Column(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = localDate.toRequiredFormat())
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(
                            DarkForestGreenColor
                        ))
                }
            }
            items(recordWithCategoryAndAccounts.size) { index ->
                ListItemRecord(
                    iconWidth = DpSize(30.dp, 30.dp),
                    record = recordWithCategoryAndAccounts[index],
                    onItemClick = {
                        onItemClick(recordWithCategoryAndAccounts[index])
                    })
            }
            item{
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))
            }

        }
    }
}





