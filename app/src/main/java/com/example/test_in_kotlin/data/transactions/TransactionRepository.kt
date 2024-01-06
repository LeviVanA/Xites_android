package com.example.test_in_kotlin.data.transactions

import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(): List<Item>
    suspend fun getProjects(): List<Items.ProjectItem>
    suspend fun getDiensten(): List<Items.DienstItem>
    suspend fun createtransaction(item: TransactionModel)
    suspend fun clearLocalCache()
    fun getAllItemsStream(): Flow<List<TransactionModel>>
    suspend fun sendtransaction(item: TransactionModel)
}