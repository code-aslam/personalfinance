package com.example.personalfinance.ui

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Toolbar(handleDrawer : () -> Unit){
    CenterAlignedTopAppBar(
        title = { Text(text = "Personal Finance") },
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