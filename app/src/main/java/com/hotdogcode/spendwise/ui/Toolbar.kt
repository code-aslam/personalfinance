package com.hotdogcode.spendwise.ui

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.OnPrimaryTextColor
import com.hotdogcode.spendwise.ui.theme.PBGFont
import com.hotdogcode.spendwise.ui.theme.PrimaryColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.googleblue
import com.hotdogcode.spendwise.ui.theme.onPrimary
import com.hotdogcode.spendwise.ui.theme.primary
import com.hotdogcode.spendwise.ui.theme.red

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(handleDrawer : () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(brightGreen),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            IconButton(onClick = handleDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "navigation drawer menu button",
                    tint = Color.White
                )
            }
        }
        Column(modifier = Modifier.weight(3f), horizontalAlignment = Alignment.Start) {
            Text(text = "SpendWise", fontFamily = PBGFont, fontWeight = FontWeight.ExtraBold, color = Color.White)
        }
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search button",
                    tint = Color.White
                )
            }
        }
    }
//    CenterAlignedTopAppBar(
//        modifier = Modifier.height(60.dp),
//        title = { Text(text = "Personal Finance", fontFamily = PBGFont, fontWeight = FontWeight.Normal) },
//        colors = topAppBarColors(
//            containerColor = Beige
//    ),
//        navigationIcon = {
//            IconButton(onClick = handleDrawer) {
//                Icon(
//                    imageVector = Icons.Default.Menu,
//                    contentDescription = "navigation drawer menu button"
//                )
//            }
//        }
//    )
}