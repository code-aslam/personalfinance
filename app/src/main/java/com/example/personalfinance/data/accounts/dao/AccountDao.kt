package com.example.personalfinance.data.accounts.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.example.personalfinance.data.accounts.entity.Account
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAccount(account: Account): Long

    @Update
    suspend fun updateExistingAccount(account: Account)

    @Transaction
    suspend fun insertOrUpdateAccount(account: Account) : Long{
        val id = insertAccount(account)
        if (id == -1L) {
            updateExistingAccount(account)
        }
        return id
    }

    @Delete
    suspend fun deleteAccount(account: Account)

    @Query("SELECT * FROM account")
    fun getAllAccounts() : Flow<List<Account>>

    @Query("UPDATE account SET balance = :balance WHERE id = :id")
    suspend fun updateAccountBalance(id: Long, balance: Int)
}