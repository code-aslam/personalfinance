package com.hotdogcode.spendwise.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.ui.theme.MainColor

@Composable
fun ListItemCreateRecordCategory(
    iconWidth: DpSize,
    category: Category,
    onItemClick : (Category) -> Unit
){
    Column(
        modifier = Modifier
            .background(MainColor)
            .padding(5.dp)
            .clickable {
                onItemClick(category)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter = painterResource(id = category.icon),
            contentDescription = "",
            modifier = Modifier
                .width(60.dp)
                .height(60.dp)
                .clip(CircleShape)
                .border(1.dp, Color.White,CircleShape)
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = category.title)
    }

}