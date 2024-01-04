package com.example.test_in_kotlin
import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import com.nhaarman.mockitokotlin2.anyOrNull
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import com.example.test_in_kotlin.ViewModelUnitTest
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import com.example.test_in_kotlin.screens.Registration.RegistrationViewModel
import kotlinx.coroutines.test.runBlockingTest
import org.junit.runner.manipulation.Ordering
import org.mockito.MockedConstruction

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ViewModelUnitTest {

    private lateinit var viewModel: RegistrationViewModel
    private lateinit var transactionsRepository: TransactionRepository

    @Before
    fun setup() {
        transactionsRepository = mock()
        viewModel = RegistrationViewModel(transactionsrepo = transactionsRepository)
        Dispatchers.setMain(Dispatchers.Unconfined) // Set the main dispatcher for testing
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher after the test
    }

    @Test
    fun `sendLoad should call createtransaction and toHome on success`() = runBlockingTest {
        // Given
        val toHomeCallback: () -> Unit = mock()
        val context: Context = mock()

        // When
        viewModel.sendLoad(toHomeCallback, context)

        // Then
        verify(transactionsRepository).createtransaction(anyOrNull())
        verify(toHomeCallback).invoke()
        verifyNoMoreInteractions(transactionsRepository, toHomeCallback)
    }

    @Test
    fun `sendLoad should update error state on exception`() = runBlockingTest {
        // Given
        val toHomeCallback: () -> Unit = mock()
        val context: Context = mock()
        val exceptionMessage = "An error occurred"
        whenever(transactionsRepository.createtransaction(anyOrNull())).thenAnswer { throw Exception(exceptionMessage) }

        // When
        viewModel.sendLoad(toHomeCallback, context)

        // Then
        verify(toHomeCallback, never()).invoke()
        verify(transactionsRepository).createtransaction(anyOrNull())
        assert(viewModel.errorState.value == "An error occurred: $exceptionMessage")
        verifyNoMoreInteractions(transactionsRepository, toHomeCallback)
    }
}
