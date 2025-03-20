package com.hotdogcode.spendwise.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hotdogcode.spendwise.common.IconLib
import com.hotdogcode.spendwise.common.IconName
import com.hotdogcode.spendwise.ui.theme.googlelightgray
import com.hotdogcode.spendwise.ui.theme.googlelightgray2

@Composable
fun ItemWithIconTitleSubTitle(
    icon: IconName,
    title: String,
    subTitle: String? = null,
    smallTitle : String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(4.dp),
    ) {
        Image(
            painter = painterResource(id = IconLib.getIcon(icon)),
            contentDescription = "",
            modifier = Modifier
                .size(45.dp)
                .clip(RoundedCornerShape(4.dp))
                .border(1.dp, Color.White, RoundedCornerShape(4.dp))
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = title, fontWeight = FontWeight.Bold)
            if(subTitle != null) Text(subTitle)
            if(smallTitle != null){
                Text(smallTitle, color = googlelightgray2)
            }
        }
    }
}