package com.hotdogcode.spendwise.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.hotdogcode.spendwise.R
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.TransactionType
import com.hotdogcode.spendwise.common.toTitleCase
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.dividerColor
import com.hotdogcode.spendwise.ui.theme.dividerColor50
import com.hotdogcode.spendwise.ui.theme.googlelightgray
import com.hotdogcode.spendwise.ui.theme.googlelightgray2

@Composable
fun ListItemRecord(
    iconWidth: DpSize,
    record: RecordWithCategoryAndAccount,
    onItemClick: (RecordWithCategoryAndAccount) -> Unit,
    showDescription: Boolean = true
) {
    Row(
        modifier = Modifier.padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(start = 15.dp, end = 15.dp, top = 2.dp, bottom = 8.dp),
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable { onItemClick(record) }
            ) {
                val (catIcon, catName, accountIcon, accountName, amount) = createRefs()

                Box(
                    modifier = Modifier
                        .background(Color.Transparent, CircleShape)
                        .size(60.dp)
                        .constrainAs(catIcon) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = IconLib.getIcon(record.categoryIcon)),
                        contentDescription = "",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                    )
                }

                Text(
                    modifier = Modifier.constrainAs(catName) {
                        top.linkTo(parent.top)
                        bottom.linkTo(accountName.top)
                        start.linkTo(catIcon.end, margin = 5.dp)
                    },
                    text = if (record.transactionType == TransactionType.INCOME)
                        "Credit for ${record.categoryTitle.toTitleCase()}"
                    else "Spent on ${record.categoryTitle.toTitleCase()}"

                )
                Image(
                    painter = painterResource(id = IconLib.getIcon(record.accountIcon)),
                    contentDescription = "",
                    modifier = Modifier
                        .constrainAs(accountIcon) {
                            top.linkTo(catName.bottom)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(catIcon.end, margin = 5.dp)
                        }
                        .size(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .border(1.dp, Color.White, RoundedCornerShape(4.dp))
                )
                Text(
                    modifier = Modifier.constrainAs(accountName) {
                        top.linkTo(accountIcon.top)
                        bottom.linkTo(accountIcon.bottom)
                        start.linkTo(accountIcon.end, margin = 5.dp)
                    },
                    text = if (record.transactionType == TransactionType.INCOME)
                        "in ${record.accountName}"
                    else "from ${record.accountName}",
                    fontSize = 12.sp, color = googlelightgray2
                )
                Text(
                    modifier = Modifier.constrainAs(amount) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        end.linkTo(parent.end)
                    },
                    text = "₹${record.amount}",
                    fontWeight = FontWeight.Bold
                )

            }

            Spacer(modifier = Modifier.height(10.dp))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(
                        dividerColor
                    )
            )

        }
    }


}