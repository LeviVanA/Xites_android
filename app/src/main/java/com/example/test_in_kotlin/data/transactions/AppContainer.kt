package com.example.test_in_kotlin.data.transactions

import android.content.Context
import com.example.test_in_kotlin.data.users.UserRepository
import com.example.test_in_kotlin.data.users.ApiUserRepository
import com.example.test_in_kotlin.network.TransactionApi.retrofitService

interface TransactionAppContainer{
    val transactionRepository: TransactionRepository
}

class TransactionAppDataContainer(private val context: Context) : TransactionAppContainer {
    override val transactionRepository: TransactionRepository by lazy {
        ApiTransactionsRepository(retrofitService)
    }
}