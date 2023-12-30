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

    fun updateUiState(newState: LoginUiState) {
        _uiState.value = newState
    }
    fun registerUser(toHome:()->Unit,) {
        viewModelScope.launch {
            val user = UserModel(
                name =uiState.value.username,
                password = uiState.value.password
            )
            userRepository.register(user)
            toHome()
        }
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