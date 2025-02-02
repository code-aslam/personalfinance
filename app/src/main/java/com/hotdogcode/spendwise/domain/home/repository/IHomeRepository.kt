package com.hotdogcode.spendwise.domain.home.repository

interface IHomeRepository {
    suspend fun clearAllTables()
}