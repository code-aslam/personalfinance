package com.example.personalfinance.domain.home.repository

import com.example.personalfinance.data.home.entity.Record
import kotlinx.coroutines.flow.Flow

interface IHomeRepository {
    fun fetchRecords() : Flow<List<Record>>
}