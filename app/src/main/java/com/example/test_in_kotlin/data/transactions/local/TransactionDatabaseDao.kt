package com.example.test_in_kotlin.data.transactions.local

import androidx.room.Insert
import com.example.test_in_kotlin.data.transactions.GetTransaction
import com.example.test_in_kotlin.data.transactions.TransactionModel

interface TransactionDatabaseDao {
    @Insert
    suspend fun insert(item: TransactionEntity) : GetTransaction
}