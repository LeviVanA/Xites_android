package com.example.test_in_kotlin.data.transactions.api

import com.example.test_in_kotlin.data.transactions.GetTransaction
import com.example.test_in_kotlin.data.transactions.TransactionModel
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import com.example.test_in_kotlin.data.users.Bearer
import com.example.test_in_kotlin.data.users.ValidatedBearer
import com.example.test_in_kotlin.network.TransactionApiService
import com.example.test_in_kotlin.screens.Registration.RegistrationUIState
import kotlinx.coroutines.flow.MutableStateFlow

class ApiTransactionsRepository(private val transactionApiService : TransactionApiService):
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
    override suspend fun createtransaction(item: TransactionModel): GetTransaction {
        val _bearerUiState = MutableStateFlow(Bearer())
        val _validationBearerUiState = MutableStateFlow(ValidatedBearer())

        println("test")
        println(item)
        println(_bearerUiState.value.bearer)
        println(_validationBearerUiState.value.token)
        if(_bearerUiState.value.bearer.equals("")){
            return (transactionApiService.createtransaction(item, _validationBearerUiState.value.token).body())!!
        }
        else{
            return (transactionApiService.createtransaction(item, _bearerUiState.value.bearer).body())!!
        }
        //return (transactionApiService.createtransaction(item).body())!!

    }
}