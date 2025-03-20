package com.hotdogcode.spendwise.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.common.formatMoney
import com.hotdogcode.spendwise.ui.BottomShadow
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.googleprimarytext


@Composable
fun DetailHeader(
    title : String,
    onCrossClick : () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth().height(60.dp),
        elevation = 0.dp,
        shape = RectangleShape,
        backgroundColor = Color.White,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onCrossClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "Close button",
                    tint = Color.Black
                )
            }
            Text(text = title, fontSize = 18.sp, color = googleprimarytext, fontWeight = FontWeight.Bold)
        }
    }
    BottomShadow()
}