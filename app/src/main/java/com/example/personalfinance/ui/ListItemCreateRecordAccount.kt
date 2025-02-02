package com.example.personalfinance.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.personalfinance.R
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SharpMainColor

@Composable
fun ListItemCreateRecordAccount(
    iconWidth: DpSize,
    account: Account,
    onItemClick : (Account) -> Unit
){
    var expanded by remember {
        mutableStateOf(false)
    }

    Box(modifier = Modifier
        .padding(start = 15.dp, top = 5.dp, bottom = 0.dp, end = 5.dp)
        .clickable { onItemClick(account) }
        .fillMaxSize()) {
        Row(
            modifier = Modifier
                .background(MainColor)
                .fillMaxWidth()
                .height(70.dp)
                .padding(5.dp),
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
            Text(text = account.name, modifier = Modifier.weight(4f))
            Text(text = (account.balance + account.initialAmount).toString(), modifier = Modifier.weight(2f))

        }
    }

}