package com.example.personalfinance.data.accounts.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.personalfinance.data.accounts.entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateAccount(account: Account) : Long

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account")
    fun getAllAccounts() : Flow<List<Account>>
}