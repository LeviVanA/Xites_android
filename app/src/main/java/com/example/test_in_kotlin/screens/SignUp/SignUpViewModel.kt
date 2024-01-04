package com.example.test_in_kotlin.screens.SignUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.test_in_kotlin.MainApplication
import com.example.test_in_kotlin.data.users.UserModel
import com.example.test_in_kotlin.data.users.UserRepository
import com.example.test_in_kotlin.screens.Login.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState : StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _errorState = MutableStateFlow<String?>(null)
    val errorState: StateFlow<String?> = _errorState.asStateFlow()

    fun updateUiState(newState: LoginUiState) {
        _uiState.value = newState
    }
    fun registerUser(toHome: suspend () -> Unit,) {
        viewModelScope.launch {
            try {
                val user = UserModel(
                    name = uiState.value.username,
                    password = uiState.value.password
                )
                userRepository.register(user)
                updateErrorState(null)
                toHome()
            }
            catch(err:Exception){
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
                SignUpViewModel( userRepository = userRepository)
            }
        }
    }
}