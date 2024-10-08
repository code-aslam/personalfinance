package com.example.personalfinance.data.datasource.local.sqldatabase

import com.example.personalfinance.domain.home.models.Record

data class RecordDto(
    val id : Int,
    val amount : Double
)

fun RecordDto.toRecord() : Record{
    return Record(
        id = id,
        amount = amount
    )
}