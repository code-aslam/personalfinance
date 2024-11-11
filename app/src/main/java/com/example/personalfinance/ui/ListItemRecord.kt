package com.example.personalfinance.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.personalfinance.R
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.ui.theme.Beige

@Composable
fun ListItemRecord(
    iconWidth: DpSize,
    record: Record,
    onItemClick : (Record) -> Unit
) {
    Box(modifier = Modifier.padding(start = 15.dp, top = 5.dp, bottom = 5.dp, end = 15.dp).clickable { onItemClick(record) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(5.dp)
                .clip(RoundedCornerShape(5.dp))
                .border(1.dp, Color.Black, RoundedCornerShape(5.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Image(
                painter = painterResource(id = R.drawable.salary),
                contentDescription = "",
                modifier = Modifier
                    .size(iconWidth)
                    .weight(1f)
            )
            Column(
                modifier = Modifier.weight(4f)
            ) {
                Text(text = "Savings")
                Text(text = record.amount.toString())
            }

        }
    }

}