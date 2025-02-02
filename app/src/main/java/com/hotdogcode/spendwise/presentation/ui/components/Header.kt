package com.hotdogcode.spendwise.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hotdogcode.spendwise.ui.BottomShadow
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor

@Composable
fun FinanceHeader(
    dataMap: Map<String, String> = mapOf(),
    isCalculationCompleted: Boolean = false
) {
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
            dataMap.forEach { (title, value) ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    var color = DarkForestGreenColor
                    Text(text = title, color = SecondaryColor)
                    if(title == "INCOME" || title == "INCOME SO FAR" ){
                        color = DarkForestGreenColor
                    }else if(title == "EXPENSE" || title == "EXPENSE SO FAR"){
                        color = SoftPinkColor
                    }else {
                        if(isCalculationCompleted){
                            color = if(value.toDouble() < 0){
                                SoftPinkColor
                            }else{
                                DarkForestGreenColor
                            }
                        }
                    }
                    Text(value, color = color)
                }
            }
        }
    }
    BottomShadow()

}
