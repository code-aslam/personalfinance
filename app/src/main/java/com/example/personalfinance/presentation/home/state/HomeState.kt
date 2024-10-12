package com.example.personalfinance.presentation.home.state

import com.example.personalfinance.presentation.home.models.Record

data class HomeState(
    val recordList : List<Record> = emptyList()
)
