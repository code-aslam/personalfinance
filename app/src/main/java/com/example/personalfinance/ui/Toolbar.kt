package com.example.personalfinance.ui

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.personalfinance.R
import com.example.personalfinance.ui.theme.Beige
import com.example.personalfinance.ui.theme.PBGFont

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(handleDrawer : () -> Unit){
    CenterAlignedTopAppBar(
        title = { Text(text = "Personal Finance", fontFamily = PBGFont, fontWeight = FontWeight.Normal) },
        colors = topAppBarColors(
            containerColor = Beige
//        TopAppBarSmallTokens.ContainerColor.value,
//        MaterialTheme.colorScheme.applyTonalElevation(
//            backgroundColor = Beige,
//            elevation = TopAppBarSmallTokens.OnScrollContainerElevation
//        ),
//        TopAppBarSmallTokens.LeadingIconColor.value,
//        TopAppBarSmallTokens.HeadlineColor.value,
//        TopAppBarSmallTokens.TrailingIconColor.value
    ),
        navigationIcon = {
            IconButton(onClick = handleDrawer) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "navigation drawer menu button"
                )
            }
        }
    )
}