package com.hotdogcode.spendwise.presentation.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.common.formatMoney
import com.hotdogcode.spendwise.common.getCurrentDateInMonthYearFormat
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.googleprimarytext
import com.hotdogcode.spendwise.ui.theme.red
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Calendar

@Composable
fun FinanceHeader(
    dataMap: Map<String, String> = mapOf(),
    isCalculationCompleted: Boolean = false,
    date: StateFlow<Calendar> = MutableStateFlow(Calendar.getInstance()),
    onDateChange: (Int) -> Unit = {}
) {
    val currentDate = date.collectAsState()
    Box(modifier = Modifier.fillMaxWidth().height(110.dp).background(
        Color.White),
    ){
        Box(
            modifier = Modifier.fillMaxWidth().height(80.dp).background(
                brightGreen)
        ){
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(110.dp)
                .padding(start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter),
            elevation = 5.dp,
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(Color.White)
            ) {

                Row(modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .padding(start = 20.dp,top = 10.dp, end = 30.dp, bottom = 4.dp),
                    verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = { onDateChange(-1) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = "Previous Month"
                        )
                    }
                    Text(
                        text = currentDate.value.time.getCurrentDateInMonthYearFormat(),
                        fontSize = 18.sp,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp),
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )
                    IconButton(onClick = { onDateChange(1) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                            contentDescription = "Next Month"
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                ) {
                    dataMap.forEach { (title, value) ->
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            var color = DarkForestGreenColor

                            if (title == "INCOME" || title == "INCOME SO FAR") {
                                Text(
                                    text = title,
                                    color = googleprimarytext,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                color = brightGreen
                            } else if (title == "EXPENSE" || title == "EXPENSE SO FAR") {
                                Text(
                                    text = title,
                                    color = googleprimarytext,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                color = red
                            } else {
                                Text(
                                    text = title,
                                    color = googleprimarytext,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 10.dp)
                                )
                                if (isCalculationCompleted) {
                                    color = if (value.toDouble() < 0) {
                                        red
                                    } else {
                                        brightGreen
                                    }
                                }
                            }

                            Text(
                                value.formatMoney(),
                                fontWeight = FontWeight.Bold,
                                color = color,
                                fontSize = 16.sp
                            )
                        }
                    }
                }
            }
        }

    }


}
