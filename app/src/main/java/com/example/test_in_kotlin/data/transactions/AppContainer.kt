package com.example.test_in_kotlin.data.transactions

import android.content.Context
import com.example.test_in_kotlin.data.transactions.local.OfflineTransactionsRepository
import com.example.test_in_kotlin.data.transactions.local.TransactionsDatabase
import com.example.test_in_kotlin.network.TransactionApi.retrofitService

interface TransactionAppContainer{
    val offlineTransactionRepository: TransactionRepository
}

class TransactionAppDataContainer(private val context: Context) : TransactionAppContainer {
    override val offlineTransactionRepository: TransactionRepository by lazy {
        println(context)
        OfflineTransactionsRepository(retrofitService,TransactionsDatabase.getDatabase(context).transactionsDbDao)
    }
}