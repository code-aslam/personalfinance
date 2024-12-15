package com.example.personalfinance.presentation.records.components

import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import com.example.personalfinance.presentation.records.RecordsViewModel
import com.example.personalfinance.ui.BottomShadow
import com.example.personalfinance.ui.ListItemRecord
import com.example.personalfinance.ui.Toolbar
import com.example.personalfinance.ui.theme.DarkForestGreenColor
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SoftPinkColor


@Composable
fun Records(padding: PaddingValues,
            handleDrawer: () -> Unit,
            recordsViewModel: RecordsViewModel,
navController: NavHostController) {
    LaunchedEffect(Unit) {
        recordsViewModel.fetchRecords()
    }

    List(recordsViewModel, padding, handleDrawer)

}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun List(recordsViewModel: RecordsViewModel, padding: PaddingValues, handleDrawer: () -> Unit){
    val recordList by recordsViewModel.recordWithCategoryAndAccountList.collectAsState()
    var selectedRecord by remember {
        mutableStateOf(
            RecordWithCategoryAndAccount()
        )
    }


    var selectedIndex by remember {
        mutableIntStateOf(0)
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        item {
            Toolbar {
                handleDrawer()
            }
        }
        stickyHeader {
            RecordHeader(padding = padding)
            BottomShadow()
        }

        items(recordList.size) { index ->
            ListItemRecord(
                iconWidth = DpSize(30.dp, 30.dp),
                record = recordList[index],
                onItemClick = {
                    selectedIndex = index
                    selectedRecord = recordList[index]

                },
            )
        }
    }
}

@Composable
fun RecordHeader(padding: PaddingValues) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp),
        elevation = 0.dp,
        backgroundColor = MainColor
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
                Text(text = "EXPENSE", color = SecondaryColor)
                Text("1500.00", color = SoftPinkColor)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "INCOME", color = SecondaryColor)
                Text(text = "1200.00", color = DarkForestGreenColor)
            }
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "TOTAL", color = SecondaryColor)
                Text(text = "-300.00", color = SoftPinkColor)
            }
        }

        
    }

}



