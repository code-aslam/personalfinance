package com.example.personalfinance.presentation.records.state

import com.example.personalfinance.presentation.records.models.Record

data class HomeState(
    val recordList : List<Record> = emptyList()
)
