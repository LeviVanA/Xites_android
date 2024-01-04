package com.example.test_in_kotlin

import android.content.Context
import com.example.test_in_kotlin.data.users.UserRepository
import com.example.test_in_kotlin.screens.Login.LoginViewModel
import com.example.test_in_kotlin.screens.Registration.RegistrationViewModel
import com.example.test_in_kotlin.screens.SignUp.SignUpViewModel
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever

@RunWith(MockitoJUnitRunner::class)
class LoginViewModelUnitTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var loginViewModel: LoginViewModel


    @Before
    fun setup() {
        userRepository = mock()
        loginViewModel = LoginViewModel(userRepository = userRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }
    @Test
    fun `loginUser should call login and toHome on success`() = runBlockingTest {
        // Given
        val toHomeCallback: () -> Unit = mock()

        // When
        loginViewModel.loginUser(toHomeCallback)

        // Then
        verify(userRepository).login(anyOrNull())
        verify(toHomeCallback).invoke()
        verifyNoMoreInteractions(userRepository, toHomeCallback)
    }
    @Test
    fun `registerUser should update error state on exception`() = runBlockingTest {

        // Given
        val toHomeCallback: () -> Unit = mock()
        //val context: Context = mock()
        val exceptionMessage = "An error occurred"

        // Mock userRepository behavior
        whenever(userRepository.login(anyOrNull())).thenAnswer { throw Exception(exceptionMessage) }

        // When
        loginViewModel.loginUser(toHomeCallback)

        // Then
        verify(toHomeCallback, never()).invoke()
        verify(userRepository).login(anyOrNull())
        assert(loginViewModel.errorState.value == "An error occurred: $exceptionMessage")
        verifyNoMoreInteractions(userRepository, toHomeCallback)
    }
}
