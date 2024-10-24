package com.example.personalfinance.presentation.records.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.example.personalfinance.ui.theme.CharcoalGrey
import com.example.personalfinance.ui.theme.DeepBurgundy

@Composable
fun Records(padding : PaddingValues){
    Card(
        modifier = Modifier.padding(padding).fillMaxWidth().height(60.dp),
        elevation = 5.dp,
        backgroundColor = Color(245, 222, 179)
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "EXPENSE")
                Text("1500.00",  color = DeepBurgundy)
            }
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "INCOME")
                Text(text = "1200.00",  color = CharcoalGrey)
            }
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text(text = "TOTAL")
                Text(text = "2700.00", color = CharcoalGrey)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun preview(){
    Records(padding = PaddingValues(16.dp))
}