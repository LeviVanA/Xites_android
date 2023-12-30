package com.example.test_in_kotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.test_in_kotlin.screens.Home.HomeScreen
import com.example.test_in_kotlin.screens.Login.LoginScreen
import com.example.test_in_kotlin.screens.Login.LoginViewModel
import com.example.test_in_kotlin.screens.Registration.RegistrationScreen
import com.example.test_in_kotlin.screens.Registrations.Registrations
import com.example.test_in_kotlin.screens.SignUp.SignUpScreen
import com.example.test_in_kotlin.ui.theme.Test_in_kotlinTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            Test_in_kotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

enum class Screen(@StringRes val title: Int) {
    Login(title = R.string.login),
    Registratie(title = R.string.registration),
    Registraties(title = R.string.registrations),
    SignUp(title = R.string.signup),
    Home(title = R.string.home);
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(navController: NavHostController = rememberNavController()){
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = Screen.valueOf(backStackEntry?.destination?.route ?: Screen.Login.name)

    val toRegistratie = { navController.navigate(Screen.Registratie.name)}
    val toRegistraties = { navController.navigate(Screen.Registraties.name)}
    val toLogin = { navController.navigate(Screen.Login.name)}
    val toSignUp = { navController.navigate(Screen.SignUp.name)}
    val toHome = { navController.navigate(Screen.Home.name)}
    Scaffold{
            innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Screen.Login.name){
                LoginScreen(toHome, toSignUp, modifier = Modifier
                )
            }
            composable(route = Screen.Registratie.name){
                RegistrationScreen(
                    toHome, modifier=Modifier
                )
            }
            composable(route = Screen.Registraties.name){
                Registrations(
                    modifier=Modifier, toHome
                )
            }
            composable(route = Screen.SignUp.name){
                SignUpScreen(toHome,
                    modifier=Modifier
                )
            }
            composable(route = Screen.Home.name){
                HomeScreen(
                    toLogin, toRegistratie, toRegistraties,
                    modifier=Modifier
                )
            }
        }
    }
}
@Preview
@Composable
fun Preview() {
    Test_in_kotlinTheme {

    }
}