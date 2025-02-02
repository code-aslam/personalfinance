package com.hotdogcode.spendwise.presentation.records.state

import com.hotdogcode.spendwise.presentation.records.models.Record

data class HomeState(
    val recordList : List<Record> = emptyList()
)
