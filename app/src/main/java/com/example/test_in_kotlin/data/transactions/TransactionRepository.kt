package com.example.test_in_kotlin.data.transactions

import com.example.test_in_kotlin.data.transactions.api.Item
import com.example.test_in_kotlin.data.transactions.api.Items

interface TransactionRepository {
    suspend fun getTransactions(): List<Item>
    suspend fun getProjects(): List<Items.ProjectItem>
    suspend fun getDiensten(): List<Items.DienstItem>
    suspend fun createtransaction(item: TransactionModel): GetTransaction
}