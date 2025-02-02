package com.example.personalfinance.domain.home.repository

interface IHomeRepository {
    suspend fun clearAllTables()
}