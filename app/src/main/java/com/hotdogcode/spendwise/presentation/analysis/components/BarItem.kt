package com.hotdogcode.spendwise.presentation.analysis.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.ui.theme.dividerColor

@Composable
fun BarItem(
    bar: Bar,
    onItemClick: (Bar) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth().height(70.dp)
            .clickable { onItemClick(bar) }
            .padding(10.dp, end = 20.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = IconLib.getIcon(bar.categoryIcon)),
                contentDescription = "",
                modifier = Modifier.size(40.dp).clip(CircleShape).weight(1f),
            )
            Column(
                modifier = Modifier.weight(5f).padding(start = 10.dp)
            ) {
                Row(modifier = Modifier.height(30.dp).fillMaxWidth()) {
                    Text(bar.categoryName, fontSize = 16.sp)
                    Spacer(modifier = Modifier.weight(1f))
                    Text("₹${bar.spending}", fontSize = 16.sp, fontWeight = FontWeight.W500)
                }
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp)
                    .border(1.dp, Color.Black, RectangleShape),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(fraction = bar.percentage.toFloat() / 100f)
                            .height(10.dp)
                            .background(bar.color),
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Text("${bar.percentage}%", fontSize = 16.sp, fontWeight = FontWeight.W500)

        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                dividerColor
            ))
    }

}