package com.hotdogcode.spendwise.presentation.ui.components

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.DialogWindowProvider

@Composable
fun BlankDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    if (showDialog) {
        androidx.compose.ui.window.Dialog(
            onDismissRequest = onDismiss
        ) {
            (LocalView.current.parent as DialogWindowProvider).window.setLayout(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            Box(modifier = Modifier.fillMaxSize()){
                content()
            }
        }
    }
}
