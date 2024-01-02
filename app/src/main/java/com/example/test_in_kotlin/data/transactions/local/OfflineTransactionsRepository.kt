package com.example.test_in_kotlin.data.transactions.local

import com.example.test_in_kotlin.data.transactions.GetTransaction
import com.example.test_in_kotlin.data.transactions.api.Item
import com.example.test_in_kotlin.data.transactions.api.Items
import com.example.test_in_kotlin.data.transactions.TransactionModel
import com.example.test_in_kotlin.data.transactions.TransactionRepository

class OfflineTransactionsRepository(private val transactionDao: TransactionDatabaseDao) :
    TransactionRepository {

    override suspend fun getTransactions(): List<Item> {
        TODO("Not yet implemented")
    }

    override suspend fun getProjects(): List<Items.ProjectItem> {
        TODO("Not yet implemented")
    }

    override suspend fun getDiensten(): List<Items.DienstItem> {
        TODO("Not yet implemented")
    }

    override suspend fun createtransaction(item: TransactionModel): GetTransaction {
        println("test 2")
        val transaction = TransactionEntity(
            dienst = item.dienst,
            project = item.project,
            beschrijving = item.beschrijving,
            kilometers = item.kilometers,
            factureerbaar = item.factureerbaar,
            tijdsduur = item.tijdsduur,
            teControleren = item.teControleren,
            date = item.date
        )

        return transactionDao.insert(transaction)
    }
}