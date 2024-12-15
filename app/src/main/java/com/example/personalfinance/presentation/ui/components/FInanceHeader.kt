package com.example.personalfinance.presentation.ui.components

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
import com.example.personalfinance.ui.BottomShadow
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SoftPinkColor

@Composable
fun FinanceHeader(
    dataMap: Map<String, String> = mapOf()
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
                    Text(text = title, color = SecondaryColor)
                    Text(value, color = SoftPinkColor)
                }
            }
        }
    }
    BottomShadow()

}
