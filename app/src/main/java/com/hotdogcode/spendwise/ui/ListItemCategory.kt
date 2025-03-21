package com.hotdogcode.spendwise.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.common.toTitleCase
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor

@Composable
fun ListItemCategory(
    category: Category,
    iconWidth: DpSize,
    menuAction: (String) -> Unit,
    onItemClick : (Category) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedItem = remember {
        mutableStateOf(
            Category(
                title = "test",
                icon = IconName.SPORT,
                type = CategoryType.INCOME
            )
        )
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(10.dp)
            .clickable { onItemClick(category) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = IconLib.getIcon(category.icon)),
            contentDescription = "",
            modifier = Modifier
                .size(iconWidth)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = category.title.toTitleCase(), modifier = Modifier.weight(4f), fontSize = 18.sp, color = Color.Black)

        Box(
            modifier = Modifier.weight(1f),
            contentAlignment = Alignment.CenterEnd
        ){
            Image(
                painter = painterResource(id = R.drawable.dots),
                contentDescription = "",
                modifier = Modifier
                    .size(DpSize(25.dp, 18.dp))
                    .clickable { expanded = true }
            )
            DropdownMenu(
                modifier = Modifier
                    .background(MainColor)
                    .width(200.dp)
                    .shadow(elevation = 1.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                listOf("Edit", "Delete").forEach { option ->
                    DropdownMenuItem(onClick = {
                        expanded = false
                        menuAction(option)
                    }) {
                        Text(text = option, color = Color.Black)
                    }
                }
            }
        }
    }
}