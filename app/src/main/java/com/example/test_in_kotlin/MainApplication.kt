package com.example.test_in_kotlin

import android.app.Application
import com.example.test_in_kotlin.data.transactions.TransactionAppContainer
import com.example.test_in_kotlin.data.transactions.TransactionAppDataContainer
import com.example.test_in_kotlin.data.users.UserAppDataContainer
import com.example.test_in_kotlin.data.users.UserAppContainer

class MainApplication : Application() {
    lateinit var transactionContainer : TransactionAppContainer
    lateinit var userContainer : UserAppContainer


    override fun onCreate(){
        super .onCreate()
        transactionContainer = TransactionAppDataContainer(applicationContext)
        userContainer = UserAppDataContainer(applicationContext)
    }
}