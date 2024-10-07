package com.example.personalfinance.domain.home.repository

import com.example.personalfinance.domain.home.models.Record

interface IHomeRepository {
    fun fetchRecords() : List<Record>
}