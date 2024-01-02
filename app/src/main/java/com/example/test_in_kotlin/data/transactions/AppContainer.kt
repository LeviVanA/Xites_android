package com.example.test_in_kotlin.data.transactions

import android.content.Context
import com.example.test_in_kotlin.data.transactions.api.ApiTransactionsRepository
import com.example.test_in_kotlin.data.transactions.local.OfflineTransactionsRepository
import com.example.test_in_kotlin.data.transactions.local.TransactionsDatabase
import com.example.test_in_kotlin.network.TransactionApi.retrofitService

interface TransactionAppContainer{
    val transactionRepository: TransactionRepository
    val offlineTransactionRepository: TransactionRepository
}

class TransactionAppDataContainer(private val context: Context) : TransactionAppContainer {
    override val transactionRepository: TransactionRepository by lazy {
        ApiTransactionsRepository(retrofitService)
    }
    override val offlineTransactionRepository: TransactionRepository by lazy {
        OfflineTransactionsRepository(TransactionsDatabase.getDatabase(context).transactionsDbDao)
    }
}