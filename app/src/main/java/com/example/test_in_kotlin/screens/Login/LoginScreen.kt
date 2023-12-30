package com.example.test_in_kotlin.screens.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.test_in_kotlin.components.Buttons
import com.example.test_in_kotlin.components.CustomTextField
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test_in_kotlin.components.TopB


@Composable
fun LoginScreen(toHome:() ->Unit,toSignUp:() ->Unit, modifier: Modifier,
                loginViewModel: LoginViewModel = viewModel(factory = LoginViewModel.Factory)) {
    val loginUIState = loginViewModel.uiState.collectAsState()
    Row{
        TopB(modifier = modifier.align(Alignment.Top))
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Spacer(modifier = Modifier.height(16.dp))

        // Username input
        CustomTextField(
            modifier = modifier.padding(15.dp, 0.dp),
            label = "Username",
            onValueChange = { updatedValue ->
                val updatedUiState = loginUIState.value.copy(username = updatedValue)
                loginViewModel.updateUiState(updatedUiState)},
            value = loginUIState.value.username,
            onTrailingIconClick = { "" },
            width = 175.dp)
        Spacer(modifier = Modifier.height(8.dp))

        // Password input
        CustomTextField(
            modifier = modifier.padding(15.dp, 0.dp),
            label = "Password",
            onValueChange = { updatedValue ->
                val updatedUiState = loginUIState.value.copy(password = updatedValue)
                loginViewModel.updateUiState(updatedUiState)},
            value = loginUIState.value.password,
            onTrailingIconClick = { "" },
            width = 175.dp,
            password = false)
        Spacer(modifier = Modifier.height(16.dp))

        // Login button
        Buttons(
            text = "Login",
            onClick = {
                loginViewModel.loginUser(toHome)
            }
        )

        // Navigation to registration screen
        Spacer(modifier = Modifier.height(16.dp))
        Buttons(
            text = "Register",
            onClick = {
                toSignUp()
            }
        )
        val errorMessage = loginViewModel.errorState.collectAsState().value
        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { loginViewModel.updateErrorState(null) }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(it)
            }
        }
    }
}
