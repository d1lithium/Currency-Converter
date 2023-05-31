package com.moin.currency_converter.domain

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moin.currency_converter.com.moin.currency_converter.domain.testCollectAsState
import com.moin.currency_converter.data.ConvertedCurrency
import com.moin.currency_converter.data.CurrencyGridState
import com.moin.currency_converter.data.local.DatabaseDriverFactory
import com.moin.currency_converter.data.local.LocalCCDataSourceImpl
import com.moin.currency_converter.data.local.LocalConvertedCurrency
import com.moin.currency_converter.data.remote.OpenExchangeAPIImpl
import com.squareup.sqldelight.db.SqlDriver
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import okhttp3.internal.wait
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule

import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class CurrencyViewModelTest: TestCase() {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var sqlDriver: SqlDriver


    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        sqlDriver = DatabaseDriverFactory(context).createDriver()
        Dispatchers.setMain(testDispatcher)



    }


    @Test
    fun getLatest_emit_success() = kotlinx.coroutines.test.runTest {
        viewModel = CurrencyViewModel(sqlDriver)
        viewModel.exchangeAPI = mockk()
        viewModel.localCCDataSource = mockk()
        coEvery {
            viewModel.exchangeAPI.getHistoricalRates()}.returns(JsonObject(mapOf()))
        coEvery {
            viewModel.exchangeAPI.getCurrencies()}.returns(JsonObject(mapOf()))
        coEvery {viewModel.localCCDataSource.getAllCCRows()}.returns(listOf(
            LocalConvertedCurrency(id=23469, base="USD", code="AED", name="", value_=3.672961.toString(), created="2023-05-30T00:29:29.474".toLocalDateTime()),
            LocalConvertedCurrency(id=23470, base="USD", code="AFN", name="", value_=86.090416.toString(), created="2023-05-30T00:29:29.476".toLocalDateTime())
        ))
        coEvery {viewModel.localCCDataSource.deleteAllCCRows()}.returns(Unit)
        coEvery {viewModel.localCCDataSource.insertCCRow(any())}.returns(Unit)

        viewModel.getLatest("USD","10")

        val expectedConvertedCurrencyList = listOf(
            ConvertedCurrency(code="AED", name="", value="36.72"),
            ConvertedCurrency(code="AFN", name="", value="860.90")
        )
        val expectedGridState = CurrencyGridState.Success(expectedConvertedCurrencyList)

        testDispatcher.scheduler.advanceUntilIdle()
        println("expected: "+expectedGridState)
        println("gridState: "+viewModel.gridState.value)
        assertEquals(expectedGridState, viewModel.gridState.value)
        println("test ended")
    }

    @Test
    fun getLatest_emit_error() = kotlinx.coroutines.test.runTest {
        viewModel = CurrencyViewModel(sqlDriver)
        viewModel.exchangeAPI = mockk()
        viewModel.localCCDataSource = mockk()
        coEvery {
            viewModel.exchangeAPI.getHistoricalRates()}.returns(JsonObject(mapOf()))
        coEvery {
            viewModel.exchangeAPI.getCurrencies()}.returns(JsonObject(mapOf()))
        coEvery {viewModel.localCCDataSource.getAllCCRows()}.returns(listOf(
            LocalConvertedCurrency(id=23469, base="USD", code="AED", name="", value_=3.672961.toString(), created="2023-05-30T00:29:29.474".toLocalDateTime()),
            LocalConvertedCurrency(id=23470, base="USD", code="AFN", name="", value_=86.090416.toString(), created="2023-05-30T00:29:29.476".toLocalDateTime())
        ))
        coEvery {viewModel.localCCDataSource.deleteAllCCRows()}.returns(Unit)
        coEvery {viewModel.localCCDataSource.insertCCRow(any())}.returns(Unit)

        viewModel.getLatest("USD","null")

        val expectedConvertedCurrencyList = listOf(
            ConvertedCurrency(code="AED", name="", value="36.72"),
            ConvertedCurrency(code="AFN", name="", value="860.90")
        )
        val expectedGridState = CurrencyGridState.Error(message = "For input string: \"null\"")

        testDispatcher.scheduler.advanceUntilIdle()
        assertEquals(expectedGridState, viewModel.gridState.value)
    }
}