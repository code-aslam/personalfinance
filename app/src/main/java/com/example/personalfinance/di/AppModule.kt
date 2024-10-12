package com.example.personalfinance.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.personalfinance.data.datasource.local.sqldatabase.FinanceDataBase
import com.example.personalfinance.data.home.dao.HomeDao
import com.example.personalfinance.data.home.repository.HomeRepository
import com.example.personalfinance.domain.cleanarchitecture.coroutine.CoroutineContextProvider
import com.example.personalfinance.domain.cleanarchitecture.usecase.UseCaseExecutor
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
        ).build()
    }

    @Provides
    fun provideHomeDao(dataBase: FinanceDataBase) = dataBase.homeDao

    @Provides
    fun provideHomeRepository(homeDao: HomeDao) : IHomeRepository{
        return HomeRepository(homeDao)
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }




}