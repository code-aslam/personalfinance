package com.example.personalfinance.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.personalfinance.R

@Composable
fun ListItemCategory(@DrawableRes iconRes : Int, iconWidth : DpSize, title : String){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painterResource(id = iconRes), contentDescription = "", modifier = Modifier.size(iconWidth).weight(1f))
        Text(text = title, modifier = Modifier.weight(4f))
        Image(painter = painterResource(id = R.drawable.dots), contentDescription = "", modifier = Modifier.size(DpSize(30.dp,30.dp)).weight(1f))
    }
}