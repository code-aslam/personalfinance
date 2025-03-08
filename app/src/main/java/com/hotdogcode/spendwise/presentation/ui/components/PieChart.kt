package com.hotdogcode.spendwise.presentation.ui.components
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hotdogcode.spendwise.ui.theme.brightGreen
import ir.ehsannarmani.compose_charts.models.Pie
import ir.ehsannarmani.compose_charts.PieChart

@Composable
fun HDCPieChart(
    chartData: List<Pie>,
) {
    var data by remember { mutableStateOf(chartData) }
    var selectedPie by remember { mutableStateOf<Pie?>(null) }
    Row(
        modifier = Modifier.padding(20.dp).fillMaxWidth().height(240.dp)
    ) {

        Box(
            modifier = Modifier.weight(0.5f),
            contentAlignment = Alignment.Center
        ){
            PieChart(
                modifier = Modifier.size(180.dp),
                data = data,
                onPieClick = {
                    println("${it.label} Clicked")
                    val pieIndex = data.indexOf(it)
                    selectedPie = it
                    data = data.mapIndexed { mapIndex, pie ->
                        pie.copy(selected = pieIndex == mapIndex)
                    }
                },
                selectedScale = 1.1f,
                scaleAnimEnterSpec = spring<Float>(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                ),
                colorAnimEnterSpec = tween(300),
                colorAnimExitSpec = tween(300),
                scaleAnimExitSpec = tween(300),
                spaceDegreeAnimExitSpec = tween(300),
                style = Pie.Style.Stroke(width = 40.dp),
                selectedPaddingDegree = 0f,
                spaceDegree = 0f
            )
            selectedPie?.let {
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp)).background(brightGreen).padding(12.dp).widthIn(min = 60.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(it.label!!, fontSize = 10.sp, color = Color.White, modifier = Modifier.background(Color.Transparent))
                    Text(it.data.toString(), fontSize = 10.sp, color = Color.White, modifier = Modifier.background(Color.Transparent))
                }
            }

        }

        LazyColumn(
            modifier = Modifier.weight(0.5f)
        ) {
            items(data.size) {
                index -> Text(data[index].label!!)
            }
        }

    }

}

