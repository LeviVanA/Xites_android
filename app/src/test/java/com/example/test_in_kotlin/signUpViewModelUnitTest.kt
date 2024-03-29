package com.example.test_in_kotlin

import android.content.Context
import com.example.test_in_kotlin.data.users.UserRepository
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
class SignUpViewModelUnitTest {

    @Mock
    private lateinit var userRepository: UserRepository

    private lateinit var signUpViewModel: SignUpViewModel


    @Before
    fun setup() {
        userRepository = mock()
        signUpViewModel = SignUpViewModel(userRepository = userRepository)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }
    @Test
    fun `registerUser should call register and toHome on success`() = runBlockingTest {
        // Given
        val toHomeCallback: () -> Unit = mock()

        // When
        signUpViewModel.registerUser(toHomeCallback)

        // Then
        verify(userRepository).register(anyOrNull())
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
        whenever(userRepository.register(anyOrNull())).thenAnswer { throw Exception(exceptionMessage) }

        // When
        signUpViewModel.registerUser(toHomeCallback)

        // Then
        verify(toHomeCallback, never()).invoke()
        verify(userRepository).register(anyOrNull())
        assert(signUpViewModel.errorState.value == "An error occurred: $exceptionMessage")
        verifyNoMoreInteractions(userRepository, toHomeCallback)
    }
}
