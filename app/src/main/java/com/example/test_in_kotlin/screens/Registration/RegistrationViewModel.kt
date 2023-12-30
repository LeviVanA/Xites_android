package com.example.test_in_kotlin.screens.Registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import com.example.test_in_kotlin.MainApplication
import com.example.test_in_kotlin.data.transactions.Items
import com.example.test_in_kotlin.data.transactions.TransactionModel
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import com.example.test_in_kotlin.data.users.UserModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RegistrationViewModel(private val transactionsrepo: TransactionRepository) : ViewModel(){
    private val _uiState = MutableStateFlow(RegistrationUIState())
    val uiState : StateFlow<RegistrationUIState> = _uiState.asStateFlow()

    private val _projects = MutableLiveData<List<Items.ProjectItem>>(emptyList())
    val projects : LiveData<List<Items.ProjectItem>> get() = _projects

    private val _diensten = MutableLiveData<List<Items.DienstItem>>(emptyList())
    val diensten : LiveData<List<Items.DienstItem>> get() = _diensten

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
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val transactionsRepository = application.transactionContainer.transactionRepository
                RegistrationViewModel( transactionsrepo = transactionsRepository)
            }
        }
    }
}