package com.example.test_in_kotlin.data.transactions

import com.example.test_in_kotlin.network.TransactionApiService

class ApiTransactionsRepository(private val transactionApiService : TransactionApiService): TransactionRepository {
    override suspend fun getTransactions(): List<Item> {
        return transactionApiService.gettransactions().body()?.items ?: emptyList();
    }
    override suspend fun getProjects(): List<Items.ProjectItem> {
        return transactionApiService.getprojects().body()?.items ?: emptyList();
    }
    override suspend fun getDiensten(): List<Items.DienstItem> {
        return transactionApiService.getdiensten().body()?.items ?: emptyList();
    }
    override suspend fun createtransaction(item: TransactionModel): GetTransaction {
        return (transactionApiService.createtransaction(item).body())!!
    }
}