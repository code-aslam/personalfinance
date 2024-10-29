package com.example.personalfinance.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.ui.theme.PBGFont

@Composable
fun ListItemHeaderAccount(title : String){
    Column {
        Text(modifier = Modifier.padding(start = 12.dp, bottom = 4.dp, end = 12.dp), text = title, fontFamily = PBGFont, fontWeight = FontWeight.Bold, fontSize = 20.sp)
        Spacer(modifier = Modifier.padding(2.dp))
    }
}