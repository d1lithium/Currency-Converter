package com.moin.currency_converter

import com.moin.currency_converter.data.ConvertedCurrency
import com.moin.currency_converter.data.CurrencyGridState
import com.moin.currency_converter.domain.CurrencyViewModel
import com.squareup.sqldelight.db.SqlDriver
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test


@ExperimentalCoroutinesApi
class CurrencyViewModelTest {
    var mockSqlDriver: SqlDriver = mockk()

    @Test
    fun `getLatest should emit CurrencyGridState Success`() = runBlockingTest() {
        // Creating a test instance of CurrencyViewModel

        val viewModel = CurrencyViewModel(mockSqlDriver)

        // Invoking the getLatest method
        viewModel.getLatest("USD", "10")
        val expectedConvertedCurrencyList = listOf(
            ConvertedCurrency("EUR", "", "12.34"),
            ConvertedCurrency("GBP", "", "56.78"),

        )

        // Verifying that the gridState emits CurrencyGridState Success
        val expectedGridState = CurrencyGridState.Success(expectedConvertedCurrencyList)
        assertEquals(expectedGridState, viewModel.gridState.value)
    }

    @Test
    fun `loadCCList should return non-empty list`() = runBlockingTest {
        // Creating a test instance of CurrencyViewModel
        val viewModel = CurrencyViewModel(mockSqlDriver)

        // Invoking the loadCCList method
        val ccList = viewModel.loadCCList()

        // Verifying that the returned list is non-empty
        assert(ccList.isNotEmpty())
    }
}
