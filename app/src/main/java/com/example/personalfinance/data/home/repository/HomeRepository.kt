package com.example.personalfinance.data.home.repository

import com.example.personalfinance.data.home.dao.HomeDao
import com.example.personalfinance.domain.home.repository.IHomeRepository
import com.example.personalfinance.data.home.entity.Record
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val homeDao: HomeDao
) : IHomeRepository {
    override fun fetchRecords() : Flow<List<Record>> {
        return homeDao.getRecordList()
    }
}