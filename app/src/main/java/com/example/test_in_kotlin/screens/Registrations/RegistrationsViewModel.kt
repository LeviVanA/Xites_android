package com.example.test_in_kotlin.screens.Registrations


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
import com.example.test_in_kotlin.data.transactions.api.Item
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import kotlinx.coroutines.launch

class RegistrationsViewModel(private val transactionsrepo:TransactionRepository) : ViewModel(){
    //private val registrationRepository = RegistrationRepository(api = retrofit.create(RegistrationApi::class.java))

    // Use mutableStateOf for ViewModel state
    private val _registrations = mutableStateOf<List<Item>>(emptyList())
    val registrations: MutableState<List<Item>> = _registrations



    // Function to fetch registrations from the backend
    fun fetchRegistrations() {
        viewModelScope.launch {
            val response = transactionsrepo.getTransactions()
            println(response + "viewmodel1")
            if(!response.isEmpty())
            {
                println(response + "viewmodel")
                _registrations.value = response
                println(registrations.value)
            }

        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val transactionsRepository = application.transactionContainer.transactionRepository
                RegistrationsViewModel( transactionsrepo = transactionsRepository)
            }
        }
    }
}