package com.hotdogcode.spendwise.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.hotdogcode.spendwise.ui.theme.MainColor
import com.hotdogcode.spendwise.ui.theme.SecondaryColor
import com.hotdogcode.spendwise.ui.theme.brightGreen
import com.hotdogcode.spendwise.ui.theme.red

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
        modifier = Modifier.background(Color.White, RoundedCornerShape(5.dp)).padding(5.dp),
        onDismissRequest = { onDismiss() },
        title = {
            Text(title,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold)
                },
        text = {
            content()
        },
        confirmButton = {
            Button(
                modifier = Modifier
                    .background(brightGreen, RoundedCornerShape(5.dp))
                    .border(1.dp, brightGreen, RoundedCornerShape(5.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = brightGreen, // Set the background color
                    contentColor = Color.White // Set the text color
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
                    .background(red, RoundedCornerShape(5.dp))
                    .border(1.dp, red, RoundedCornerShape(5.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = red, // Set the background color
                    contentColor = Color.White // Set the text color
                ),
                onClick = { onDismiss()}) {
                Text(dismissText)
            }
        }
    )
}