package com.example.personalfinance.presentation.ui.components

import androidx.compose.material.TextButton
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.personalfinance.common.toRequiredFormat
import com.example.personalfinance.presentation.createrecord.CreateRecordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModel(
    onTimeSelected: (String) -> Unit,
    onDismiss: () -> Unit,
    createRecordViewModel: CreateRecordViewModel
) {
    val showtimePicker by createRecordViewModel.showTimePicker.collectAsState()
    if(showtimePicker) {
        val timePickerState = rememberTimePickerState()

        DatePickerDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelected(timePickerState.toRequiredFormat())
                    onDismiss()
                }) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        ) {
            TimePicker(state = timePickerState)
        }
    }
}