package com.example.test_in_kotlin.screens.Registration

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.test_in_kotlin.MainApplication
import com.example.test_in_kotlin.data.transactions.api.Items
import com.example.test_in_kotlin.data.transactions.TransactionModel
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import com.example.test_in_kotlin.data.transactions.local.ConnectionState
import com.example.test_in_kotlin.data.transactions.local.TransactionEntity
import com.example.test_in_kotlin.data.transactions.local.checkIfAnyCached
import com.example.test_in_kotlin.data.transactions.local.getCurrentConnectivityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(private val transactionsrepo: TransactionRepository) : ViewModel(){
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState : StateFlow<RegistrationUIState> = _uiState.asStateFlow()


    private val _projects = mutableStateOf<List<Items.ProjectItem>>(emptyList())
    val projects : MutableState<List<Items.ProjectItem>> = _projects

    private val _diensten = mutableStateOf<List<Items.DienstItem>>(emptyList())
    val diensten : MutableState<List<Items.DienstItem>> get() = _diensten

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    fun sendLoad(toHome: () ->Unit) {
        viewModelScope.launch {

            try {
                val transaction = TransactionModel(
                    dienst = uiState.value.dienst,
                    project = uiState.value.project,
                    beschrijving = uiState.value.beschrijving,
                    kilometers = uiState.value.kilometers,
                    factureerbaar = uiState.value.factureerbaar,
                    tijdsduur = uiState.value.tijdsduur,
                    teControleren = uiState.value.teControleren,
                    date = uiState.value.date
                )
                println(uiState.value.date)
                println(transaction)
                transactionsrepo.createtransaction(transaction)
                toHome()
            }
            catch(err : Exception){
                _errorState.value = "An error occurred: ${err.message}"
            }
        }
    }

    fun updateUiState(uiState: RegistrationUIState) {
        _uiState.value = uiState
    }
    fun fetchDienstenProjects() {
        viewModelScope.launch {
            val projectResponse = transactionsrepo.getProjects()
            val dienstResponse = transactionsrepo.getDiensten()

            _projects.value = projectResponse
            _diensten.value = dienstResponse

            println(projects.value)
            println(diensten.value)
        }
    }
    fun updateErrorState(errorMessage: String?) {
        _errorState.value = errorMessage
    }
    fun cacheCheck(ctx: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            if (ctx.getCurrentConnectivityState() != ConnectionState.Available || !checkIfAnyCached(
                    transactionsrepo
                )
            ) {
                Log.d("Xites_CACHE", "no cache or no network")
                return@launch
            }

        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val transactionsRepository = application.transactionContainer.offlineTransactionRepository
                RegistrationViewModel( transactionsrepo = transactionsRepository)
            }
        }
    }
}