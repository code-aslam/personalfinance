package com.example.personalfinance.ui

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.personalfinance.R
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.PBGFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(handleDrawer : () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(Beige),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(0.5f)
        ) {
            IconButton(onClick = handleDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "navigation drawer menu button"
                )
            }
        }
        Column(modifier = Modifier.weight(3f), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Personal Finance", fontFamily = PBGFont, fontWeight = FontWeight.Normal)
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