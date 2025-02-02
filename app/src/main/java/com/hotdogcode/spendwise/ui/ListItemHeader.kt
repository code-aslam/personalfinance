package com.hotdogcode.spendwise.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.ui.theme.SecondaryColor

@Composable
fun ListItemHeader(title : String){
    Column {
        Text(modifier = Modifier.padding(start = 12.dp, bottom = 4.dp, end = 12.dp), text = title, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = SecondaryColor)
        Spacer(modifier = Modifier.padding(2.dp))
        Box(modifier = Modifier
            .padding(start = 12.dp, bottom = 4.dp, end = 12.dp)
            .fillMaxWidth()
            .height(1.dp)
            .background(SecondaryColor))
    }
}

@Preview(showBackground = true)
@Composable
fun test(){
    ListItemHeader(title = "Income Category")
}