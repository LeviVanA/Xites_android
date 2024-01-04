package com.example.test_in_kotlin.screens.SignUp

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.test_in_kotlin.components.Buttons
import com.example.test_in_kotlin.components.CustomTextField
import com.example.test_in_kotlin.components.TopB
import com.example.test_in_kotlin.screens.Registrations.RegistrationsViewModel


@Composable
fun SignUpScreen(toHome:()->Unit, modifier: Modifier, signUpViewModel: SignUpViewModel = viewModel(factory = SignUpViewModel.Factory)) {
    val signUpUIState = signUpViewModel.uiState.collectAsState()
    var confirmPassword by remember { mutableStateOf("") }
    var isError by rememberSaveable { mutableStateOf(false) }

    Row{
        TopB(modifier = modifier.align(Alignment.Top))
    }
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Registration form header
        Text(text = "Create an Account", style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold))
        Spacer(modifier = Modifier.height(16.dp))

        // Other registration UI components (e.g., date picker, dropdowns, etc.)


        // Username input
        CustomTextField(
            modifier = modifier.padding(15.dp, 0.dp),
            label = "Username",
            onValueChange = { updatedValue ->
                val updatedUiState = signUpUIState.value.copy(username = updatedValue)
                signUpViewModel.updateUiState(updatedUiState)},
            value = signUpUIState.value.username,
            onTrailingIconClick = { "" },
            width = 175.dp)
        Spacer(modifier = Modifier.height(8.dp))

        // Password input
        CustomTextField(
            modifier = modifier.padding(15.dp, 0.dp),
            label = "Password",
            onValueChange = { updatedValue ->
                val updatedUiState = signUpUIState.value.copy(password = updatedValue)
                signUpViewModel.updateUiState(updatedUiState)},
            value = signUpUIState.value.password,
            onTrailingIconClick = { "" },
            width = 175.dp,
            password = false)
        CustomTextField(
            modifier = modifier.padding(15.dp, 0.dp),
            label = "Confirm Password",
            onValueChange = { confirmPassword = it},
            value = confirmPassword,
            onTrailingIconClick = { "" },
            width = 175.dp,
            password = false)
        Spacer(modifier = Modifier.height(16.dp))
        if(isError==true){
        Snackbar(
            modifier = Modifier.padding(16.dp),
            action = {
                TextButton(onClick = { isError=false }) {
                    Text("Dismiss")
                }
            }
        ) {
            Text("confirm password isn't the same as password")
        }}

        // Registration button
        Buttons(
            text = "Register",
            onClick = {
                if(signUpUIState.value.password != confirmPassword)
                {
                    isError = true
                }
                else
                {
                    signUpViewModel.registerUser(toHome)

                }
                // Handle registration logic
            }
        )
        val errorMessage = signUpViewModel.errorState.collectAsState().value
        errorMessage?.let {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { signUpViewModel.updateErrorState(null) }) {
                        Text("Dismiss")
                    }
                }
            ) {
                Text(it)
            }
        }
    }
}

