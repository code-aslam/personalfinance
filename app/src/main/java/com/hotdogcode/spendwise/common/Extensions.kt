package com.hotdogcode.spendwise.common

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import com.google.gson.Gson
import com.hotdogcode.spendwise.data.record.entity.RecordWithCategoryAndAccount
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

fun Date.toRequiredFormat() : String{
    return SimpleDateFormat("dd MMM yyyy", Locale.US).format(this)
}

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toRequiredFormat() : String{
    return format(DateTimeFormatter.ofPattern("dd MMM yyyy")).format(this)
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

fun String.toTitleCase():String{
    return this.replaceFirstChar {
        if (it.isLowerCase()) it.titlecase(
            Locale.ROOT
        ) else it.toString()
    }
}

fun String.formatMoney(): String {
    val numberFormat = NumberFormat.getInstance(Locale("en", "IN")) // Indian locale
    return if(this.toDoubleOrNull() != null)
        numberFormat.format(this.toDouble())
    else this
}

fun String.fromJsonStringToRecordWithCategoryAndAccount() = Gson().fromJson(this, RecordWithCategoryAndAccount::class.java)
