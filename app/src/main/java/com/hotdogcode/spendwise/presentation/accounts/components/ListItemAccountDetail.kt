package com.hotdogcode.spendwise.presentation.accounts.components

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.CategoryType
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.common.toTitleCase
import com.hotdogcode.spendwise.data.accounts.entity.Account
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.dividerColor

@Composable
fun ListItemAccountDetail(
    record : RecordWithCategoryAndAccount,
    iconWidth: DpSize,
    onItemClick : (Category) -> Unit
) {


    Column(
        modifier = Modifier.fillMaxWidth()
            .height(70.dp)
            .padding(start = 10.dp, end = 10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().height(65.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = IconLib.getIcon(record.categoryIcon)),
                contentDescription = "",
                modifier = Modifier
                    .size(iconWidth)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = if (record.transactionType == TransactionType.INCOME)
                "Credited for ${record.categoryTitle.toTitleCase()}"
            else "Spent on ${record.categoryTitle.toTitleCase()}", modifier = Modifier.weight(4f), fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.W500)

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "₹${record.amount}",
                fontWeight = FontWeight.Bold
            )

        }

        Spacer(modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                dividerColor
            ))
    }

}