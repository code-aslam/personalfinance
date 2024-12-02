package com.example.personalfinance.common

import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.toRequiredFormat() : String{
    return SimpleDateFormat("dd MMM yyyy", Locale.US).format(this)
}

fun Date.toRequiredTimeFormat() : String{
    return SimpleDateFormat("hh:mm a", Locale.US).format(this)
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.getAmPm(): String {
    // Assuming hour is in 24-hour format
    return if (this.hour < 12) "AM" else "PM"
}

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toRequiredFormat() : String{
    val hour = if (this.hour < 12) this.hour else this.hour - 12
    return "${hour.toString().padStart(2, '0')}:${this.minute.toString().padStart(2, '0')} ${this.getAmPm()}"
}

@OptIn(ExperimentalMaterial3Api::class)
fun DatePickerState.toRequireFormat(): String {
    return selectedDateMillis?.let {
        dateStr-> Date(dateStr).toRequiredFormat()
    } ?: Date().getCurrentDateInRequireFormat()
}

fun Date.getCurrentDateInRequireFormat():String{
    return SimpleDateFormat("dd MMM yyyy", Locale.US).format(this)
}