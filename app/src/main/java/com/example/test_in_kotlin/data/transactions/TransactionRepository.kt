package com.example.test_in_kotlin.data.transactions

interface TransactionRepository {
    suspend fun getTransactions(): List<Item>
    suspend fun getProjects(): List<Items.ProjectItem>
    suspend fun getDiensten(): List<Items.DienstItem>
    suspend fun createtransaction(item: TransactionModel): GetTransaction
}