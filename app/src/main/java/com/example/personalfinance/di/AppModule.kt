package com.example.personalfinance.di

import android.content.Context
import androidx.room.Room
import com.example.personalfinance.data.accounts.dao.AccountDao
import com.example.personalfinance.data.accounts.repository.AccountRepository
import com.example.personalfinance.data.category.dao.CategoryDao
import com.example.personalfinance.data.category.repository.CategoryRepository
import com.example.personalfinance.data.datasource.local.sqldatabase.FinanceDataBase
import com.example.personalfinance.data.record.dao.RecordDao
import com.example.personalfinance.data.record.repository.RecordRepository
import com.example.personalfinance.domain.account.repository.IAccountRepository
import com.example.personalfinance.domain.category.repository.ICategoryRepository
import com.example.personalfinance.domain.home.repository.IHomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : FinanceDataBase{
        return Room.databaseBuilder(
            context.applicationContext,
            FinanceDataBase::class.java,
            "finance-db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideHomeDao(dataBase: FinanceDataBase) = dataBase.recordDao

    @Provides
    fun provideAccountDao(dataBase: FinanceDataBase) = dataBase.accountDao

    @Provides
    fun provideCategoryDao(dataBase: FinanceDataBase) = dataBase.categoryDao

    @Provides
    fun provideHomeRepository(recordDao: RecordDao) : IHomeRepository{
        return RecordRepository(recordDao)
    }

    @Provides
    fun provideCategoryRepository(categoryDao: CategoryDao) : ICategoryRepository{
        return CategoryRepository(categoryDao)
    }

    @Provides
    fun provideAccountRepository(accountDao: AccountDao) : IAccountRepository {
        return AccountRepository(accountDao)
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

}