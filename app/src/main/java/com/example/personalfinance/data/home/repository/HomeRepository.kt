package com.example.personalfinance.data.home.repository

import com.example.personalfinance.domain.home.models.Record
import com.example.personalfinance.domain.home.repository.IHomeRepository

class HomeRepository : IHomeRepository {
    override fun fetchRecords() : List<Record>{
        return listOf(Record(0,10.0))
    }
}