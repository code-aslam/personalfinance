package com.example.personalfinance.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.personalfinance.data.accounts.entity.Account
import com.example.personalfinance.presentation.accounts.AccountViewModel
import com.example.personalfinance.ui.theme.MainColor
import com.example.personalfinance.ui.theme.SecondaryColor

@Composable
fun Dialog(title : String,
           content : @Composable () -> Unit,
           confirmText : String,
           dismissText : String,
           onConfirm: () -> Unit,
           onDismiss : () -> Unit,
) {
    AlertDialog(
        containerColor = MainColor,
        modifier = Modifier.background(Color.Transparent),
        onDismissRequest = { onDismiss() },
        title = { Text(
            title,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            color = SecondaryColor,
            fontWeight = FontWeight.Bold) },
        text = {
            content()
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .background(MainColor, RoundedCornerShape(5.dp))
                    .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor, // Set the background color
                    contentColor = SecondaryColor // Set the text color
                ),
                onClick = {
                    onDismiss()
                    onConfirm()
                }) {
                Text(confirmText)
            }
        },
        dismissButton = {
            Button(
                modifier = Modifier
                    .background(MainColor, RoundedCornerShape(5.dp))
                    .border(1.dp, SecondaryColor, RoundedCornerShape(5.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MainColor, // Set the background color
                    contentColor = SecondaryColor // Set the text color
                ),
                onClick = { onDismiss()}) {
                Text(dismissText)
            }
        }
    )
}