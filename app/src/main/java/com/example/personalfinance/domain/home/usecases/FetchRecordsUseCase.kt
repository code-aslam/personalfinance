package com.example.personalfinance.domain.home.usecases

import com.example.personalfinance.domain.home.repository.IHomeRepository

class FetchRecordsUseCase(private val repository: IHomeRepository) {
    fun execute() = repository.fetchRecords()
}