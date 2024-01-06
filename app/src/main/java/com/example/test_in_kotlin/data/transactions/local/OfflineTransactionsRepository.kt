package com.example.test_in_kotlin.data.transactions.local

import com.example.test_in_kotlin.data.transactions.Item
import com.example.test_in_kotlin.data.transactions.Items
import com.example.test_in_kotlin.data.transactions.TransactionModel
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import com.example.test_in_kotlin.data.users.Bearer
import com.example.test_in_kotlin.data.users.ValidatedBearer
import com.example.test_in_kotlin.network.TransactionApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class OfflineTransactionsRepository(private val transactionApiService : TransactionApiService, private val transactionDao: TransactionDatabaseDao) :
    TransactionRepository {

    override suspend fun getTransactions(): List<Item> {
        return transactionApiService.gettransactions().body()?.items ?: emptyList();
    }

    override suspend fun getProjects(): List<Items.ProjectItem> {
        return transactionApiService.getprojects().body()?.items ?: emptyList();
    }

    override suspend fun getDiensten(): List<Items.DienstItem> {
        return transactionApiService.getdiensten().body()?.items ?: emptyList();
    }

    override suspend fun createtransaction(item: TransactionModel) {
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

         transactionDao.insert(transaction)
    }
    override suspend fun clearLocalCache() {
        transactionDao.clearLocalCache()
    }
    override fun getAllItemsStream(): Flow<List<TransactionModel>> = transactionDao.getAllItems()

    override suspend fun sendtransaction(item: TransactionModel) {
        val _bearerUiState = MutableStateFlow(Bearer())
        val _validationBearerUiState = MutableStateFlow(ValidatedBearer())

        println("test")
        println(item)
        println(_bearerUiState.value.bearer)
        println(_validationBearerUiState.value.token)
        if(_bearerUiState.value.bearer.equals("")){
            (transactionApiService.createtransaction(item, _validationBearerUiState.value.token))
        }
        else{
            (transactionApiService.createtransaction(item, _bearerUiState.value.bearer))
        }
        //return (transactionApiService.createtransaction(item).body())!!

    }
}