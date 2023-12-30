package com.example.test_in_kotlin.screens.Login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.test_in_kotlin.MainApplication
import com.example.test_in_kotlin.data.users.UserModel
import com.example.test_in_kotlin.data.users.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()


    fun loginUser(toHome:()->Unit,) {
        viewModelScope.launch {
            try {
                val user = UserModel(
                    name = uiState.value.username,
                    password = uiState.value.password
                )
                userRepository.login(user)
                updateErrorState(null)
                toHome()
            }
            catch(err : Exception)
            {
                println(err)
                _errorState.value = "An error occurred: ${err.message}"
            }
        }
    }
    fun updateErrorState(errorMessage: String?) {
        _errorState.value = errorMessage
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication)
                val userRepository = application.userContainer.userRepository
                LoginViewModel( userRepository = userRepository)
            }
        }
    }
    fun updateUiState(newState: LoginUiState) {
        _uiState.value = newState
    }

}