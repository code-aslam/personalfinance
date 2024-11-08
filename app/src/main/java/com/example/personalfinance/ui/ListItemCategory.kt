package com.example.personalfinance.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.personalfinance.R
import com.example.personalfinance.common.CategoryType
import com.example.personalfinance.data.category.entity.Category
import com.example.personalfinance.ui.theme.Beige

@Composable
fun ListItemCategory(
    category: Category,
    iconWidth: DpSize,
    menuAction: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedItem = remember {
        mutableStateOf(
            Category(
                title = "test",
                icon = 0,
                type = CategoryType.INCOME
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = category.icon),
            contentDescription = "",
            modifier = Modifier
                .size(iconWidth)
                .weight(1f)
        )
        Text(text = category.title, modifier = Modifier.weight(4f))
        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterStart
        ){
            Image(
                painter = painterResource(id = R.drawable.dots),
                contentDescription = "",
                modifier = Modifier
                    .size(DpSize(30.dp, 30.dp))
                    .clickable { expanded = true }
            )
            DropdownMenu(
                modifier = Modifier
                    .background(Beige)
                    .width(200.dp)
                    .shadow(elevation = 2.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                listOf("Edit", "Delete").forEach { option ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        menuAction(option)
                    }) {
                        Text(text = option)
                    }
                }
            }
        }
    }
}