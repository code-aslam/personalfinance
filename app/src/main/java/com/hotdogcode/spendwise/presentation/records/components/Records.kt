package com.hotdogcode.spendwise.presentation.records.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.common.formatMoney
import com.hotdogcode.spendwise.common.toRequiredFormat
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.presentation.accounts.components.AccountDetails
import com.hotdogcode.spendwise.presentation.records.RecordsViewModel
import com.hotdogcode.spendwise.presentation.ui.components.BlankDialog
import com.hotdogcode.spendwise.presentation.ui.components.Dialog
import com.hotdogcode.spendwise.presentation.ui.components.FinanceHeader
import com.hotdogcode.spendwise.ui.ListItemRecord
import com.hotdogcode.spendwise.ui.Toolbar
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.background
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.dividerColor
import com.hotdogcode.spendwise.ui.theme.red
import java.time.LocalDate
import java.util.Date


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
        { FinanceHeader(dataMap, isCalculationCompleted)},
        recordsViewModel
    )

}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(
    recordList : Map<LocalDate, List<RecordWithCategoryAndAccount>>,
         padding: PaddingValues,
         handleDrawer: () -> Unit,
         header : @Composable () -> Unit,
    viewModel: RecordsViewModel){

    var selectedRecord by remember {
        mutableStateOf(
            RecordWithCategoryAndAccount()
        )
    }


    var selectedIndex by remember { mutableIntStateOf(0) }
    val showDetails by viewModel.showDetails.collectAsState()
    val showDelete by viewModel.showDelete.collectAsState()
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
            header()
        }

        item{
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp))
        }

        recordList.forEach { (localDate, recordWithCategoryAndAccounts) ->
            item {
                Column(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp)
                ) {
                    Text(text = localDate.toRequiredFormat(), fontWeight = FontWeight.Bold, fontSize = 20.sp)
                }
            }
            items(recordWithCategoryAndAccounts.size) { index ->
                ListItemRecord(
                    iconWidth = DpSize(30.dp, 30.dp),
                    record = recordWithCategoryAndAccounts[index],
                    onItemClick = {
                        selectedIndex = index
                        selectedRecord = recordWithCategoryAndAccounts[index]
                        viewModel.showDetailsAction()
                    })
            }
            item{
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp))
            }

        }
    }

    if(showDelete){
        Dialog(
            title = "Delete This Transaction",
            content = {
                Text("Are you sure, you want to delete this transaction ?", color = Color.Black, fontSize = 18.sp)
            },
            confirmText = "YES",
            onConfirm = { viewModel.deleteRecordWithId(selectedRecord.recordId)},
            dismissText = "NO",
            onDismiss = { viewModel.hideDeleteAction() }
        )
    }

    BlankDialog(
        showDialog = showDetails,
        onDismiss = {  }
    ) {
        RecordsDetails(selectedRecord,
            viewModel,
            onDismiss = {
                viewModel.hideDetailsAction()
            })
    }
}

@Composable
fun RecordsDetails(
    selectedRecord : RecordWithCategoryAndAccount,
    recordsViewModel: RecordsViewModel,
    onDismiss : () -> Unit
){
    LaunchedEffect(selectedRecord) {
        recordsViewModel.getRecord(selectedRecord.recordId)
    }

    val record by recordsViewModel.selectedRecordDetails.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
                .height(360.dp)
                .background(Color.Transparent)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth().weight(0.5f).background(if(record.transactionType == TransactionType.INCOME) brightGreen else red)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        onDismiss()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Close button",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        onDismiss()
                        recordsViewModel.showDeleteAction()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Close button",
                            tint = Color.White
                        )
                    }

                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Close button",
                            tint = Color.White
                        )
                    }
                }

                Text(record.transactionType.name,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White)

                Spacer(modifier = Modifier.height(10.dp))
                Text("₹${record.amount.toString().formatMoney()}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    fontSize = 32.sp)

                Spacer(modifier = Modifier.weight(1f))
                Text("${Date(record.date).toRequiredFormat()} ${record.time}",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    color = Color.White,
                    fontSize = 16.sp)
            }
            Column(
                modifier = Modifier.fillMaxWidth().weight(0.5f).background(Color.White)
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    Text("Account", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.salary_new),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(record.accountName, fontSize = 20.sp)
                }
                Row(modifier = Modifier.fillMaxWidth().padding(5.dp)) {
                    Text("Category", fontSize = 20.sp)
                    Spacer(modifier = Modifier.width(20.dp))
                    Image(
                        painter = painterResource(id = R.drawable.salary_new),
                        contentDescription = "",
                        modifier = Modifier
                            .size(30.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(record.categoryTitle, fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(text = record.notes.ifEmpty { "No notes" }, textAlign = TextAlign.Center)
            }
        }
    }
}





