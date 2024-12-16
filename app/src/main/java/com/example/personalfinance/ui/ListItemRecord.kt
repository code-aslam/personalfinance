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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.personalfinance.R
import com.example.personalfinance.common.toTitleCase
import com.example.personalfinance.data.record.entity.Record
import com.example.personalfinance.data.record.entity.RecordWithCategoryAndAccount
import com.example.personalfinance.ui.theme.Beige
import java.util.Locale

@Composable
fun ListItemRecord(
    iconWidth: DpSize,
    record: RecordWithCategoryAndAccount,
    onItemClick : (RecordWithCategoryAndAccount) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(start = 15.dp, end = 15.dp, top = 2.dp, bottom = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(record) }
    ) {
        val (catIcon, catName , accountIcon, accountName, amount) = createRefs()
        Image(
            painter = painterResource(id = record.categoryIcon),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(catIcon){
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
                .size(40.dp)
                .clip(CircleShape)
                .border(2.dp, Color.White, CircleShape)
        )
        Text(
            modifier = Modifier.constrainAs(catName){
                top.linkTo(parent.top)
                bottom.linkTo(accountName.top)
                start.linkTo(catIcon.end, margin = 5.dp)
            },
            text = record.categoryTitle.toTitleCase()
        )
        Image(
            painter = painterResource(id = record.accountIcon),
            contentDescription = "",
            modifier = Modifier
                .constrainAs(accountIcon){
                    top.linkTo(catName.bottom)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(catIcon.end, margin = 5.dp)
                }
                .size(20.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
        )
        Text(
            modifier = Modifier.constrainAs(accountName){
                top.linkTo(accountIcon.top)
                bottom.linkTo(accountIcon.bottom)
                start.linkTo(accountIcon.end, margin = 5.dp)
            },
            text = record.accountName,
            fontSize = 12.sp)
        Text(
            modifier = Modifier.constrainAs(amount){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            },
            text = "₹${record.amount}"
        )

    }

}