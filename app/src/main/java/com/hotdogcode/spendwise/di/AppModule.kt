package com.hotdogcode.spendwise.di

import android.content.Context
import androidx.room.Room
import com.hotdogcode.spendwise.data.accounts.dao.AccountDao
import com.hotdogcode.spendwise.data.accounts.repository.AccountRepository
import com.hotdogcode.spendwise.data.category.dao.CategoryDao
import com.hotdogcode.spendwise.data.category.repository.CategoryRepository
import com.hotdogcode.spendwise.data.datasource.local.sqldatabase.FinanceDataBase
import com.hotdogcode.spendwise.data.record.dao.RecordDao
import com.hotdogcode.spendwise.data.record.repository.RecordRepository
import com.hotdogcode.spendwise.domain.account.repository.IAccountRepository
import com.hotdogcode.spendwise.domain.category.repository.ICategoryRepository
import com.hotdogcode.spendwise.domain.record.repository.IRecordRepository
import com.hotdogcode.spendwise_ml.SpendWisePurchaseAdviser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.tensorflow.lite.Interpreter
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel
import javax.inject.Named
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
    fun provideRecordRepository(recordDao: RecordDao) : IRecordRepository{
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

    private fun loadModelFile(context: Context, modelFileName: String): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd(modelFileName)
        val fileChannel = fileDescriptor.createInputStream().channel
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, fileDescriptor.startOffset, fileDescriptor.declaredLength)
    }

    @Provides
    @Singleton
    @Named("purchaseAdviserInterpreter")
    fun providePurchaseAdviserInterpreter(@ApplicationContext context: Context): Interpreter {
        return Interpreter(loadModelFile(context,"spendwise_purchase_advisor.tflite"))
    }

    @Provides
    @Singleton
    fun provideSpendWisePurchaseAdviser(@Named("purchaseAdviserInterpreter") purchaseAdviserInterpreter: Interpreter): SpendWisePurchaseAdviser {
        return SpendWisePurchaseAdviser(purchaseAdviserInterpreter)
    }


}