package com.hotdogcode.spendwise.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.common.formatMoney
import com.hotdogcode.spendwise.ui.BottomShadow
import com.hotdogcode.spendwise.ui.theme.DarkForestGreenColor
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.SoftPinkColor
import com.hotdogcode.spendwise.ui.theme.SurfaceColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.gold
import com.hotdogcode.spendwise.ui.theme.googleblue
import com.hotdogcode.spendwise.ui.theme.googlelightgray
import com.hotdogcode.spendwise.ui.theme.googleprimarytext
import com.hotdogcode.spendwise.ui.theme.primary
import com.hotdogcode.spendwise.ui.theme.red
import com.hotdogcode.spendwise.ui.theme.secondary

@Composable
fun FinanceHeader(
    dataMap: Map<String, String> = mapOf(),
    isCalculationCompleted: Boolean = false
) {
    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(
        Color.White),
    ){
        Box(
            modifier = Modifier.fillMaxWidth().height(70.dp).background(
                brightGreen)
        ){
        }
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(start = 20.dp, end = 20.dp)
                .align(Alignment.BottomCenter),
            elevation = 5.dp,
            shape = RoundedCornerShape(5.dp),
            backgroundColor = Color.White
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                dataMap.forEach { (title, value) ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        var color = DarkForestGreenColor

                        if(title == "INCOME" || title == "INCOME SO FAR" ){
                            Text(text = title, color = googleprimarytext, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 10.dp))
                            color = brightGreen
                        }else if(title == "EXPENSE" || title == "EXPENSE SO FAR"){
                            Text(text = title, color = googleprimarytext, fontWeight = FontWeight.Bold,modifier = Modifier.padding(bottom = 10.dp))
                            color = red
                        }else {
                            Text(text = title, color = googleprimarytext, fontWeight = FontWeight.Bold,modifier = Modifier.padding(bottom = 10.dp))
                            if(isCalculationCompleted){
                                color = if(value.toDouble() < 0){
                                    red
                                }else{
                                    brightGreen
                                }
                            }
                        }

                        Text(value.formatMoney(), fontWeight = FontWeight.Bold,color = color, fontSize = 16.sp)
                    }
                }
            }
        }

    }


}
