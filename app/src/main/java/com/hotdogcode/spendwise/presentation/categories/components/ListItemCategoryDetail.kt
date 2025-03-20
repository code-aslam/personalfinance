package com.hotdogcode.spendwise.presentation.categories.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.common.toTitleCase
import com.hotdogcode.spendwise.data.category.entity.Category
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.ui.theme.dividerColor


@Composable
fun ListItemCategoryDetail(
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
                painter = painterResource(id = IconLib.getIcon(record.accountIcon)),
                contentDescription = "",
                modifier = Modifier
                    .size(iconWidth)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = if (record.transactionType == TransactionType.INCOME)
                "Credited in ${record.accountName.toTitleCase()}"
            else "Spent from ${record.accountName.toTitleCase()}", modifier = Modifier.weight(4f), fontSize = 16.sp, color = Color.Black, fontWeight = FontWeight.W500)

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