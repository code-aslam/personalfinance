package com.example.personalfinance.ui

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.personalfinance.R
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.DarkForestGreenColor
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor
import com.example.personalfinance.ui.theme.SharpMainColor

@Composable
fun ListItemAccount(
    iconWidth: DpSize,
    account: Account,
    menuAction: (String) -> Unit,
    onItemClick : (Account) -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }

        Box(modifier = Modifier
            .padding(start = 15.dp, top = 5.dp, bottom = 5.dp, end = 15.dp)
            .clickable { onItemClick(account) }) {
            Row(
                modifier = Modifier
                    .background(SharpMainColor)
                    .fillMaxWidth()
                    .height(70.dp)
                    .clip(RoundedCornerShape(5.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
                    .padding(start = 10.dp, end = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {


                Image(
                    painter = painterResource(id = account.icon),
                    contentDescription = "",
                    modifier = Modifier
                        .size(iconWidth)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(4.dp))
                Column(
                    modifier = Modifier.weight(4f)
                ) {
                    Text(text = account.name)
                    Text(text = (account.initialAmount + account.balance).toString())
                }

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.dots),
                        contentDescription = "",
                        modifier = Modifier
                            .size(DpSize(25.dp, 25.dp))
                            .clickable { expanded = true }
                    )
                    DropdownMenu(
                        modifier = Modifier
                            .background(MainColor)
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
                                Text(text = option, color = SecondaryColor)
                            }
                        }
                    }
                }
            }
        }

}