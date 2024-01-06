package com.example.test_in_kotlin.data.transactions.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.test_in_kotlin.data.transactions.GetTransaction
import com.example.test_in_kotlin.data.transactions.TransactionModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDatabaseDao {
    @Insert
    suspend fun insert(item: TransactionEntity)
    @Query("DELETE FROM transaction_table")
    suspend fun clearLocalCache()
    @Query("SELECT * from transaction_table ORDER BY id ASC")
    fun getAllItems(): Flow<List<TransactionModel>>
}