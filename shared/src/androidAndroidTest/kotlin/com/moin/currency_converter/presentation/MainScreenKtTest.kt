package com.moin.currency_converter.presentation

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.moin.currency_converter.MainViewAndroid
import com.moin.currency_converter.data.local.DatabaseDriverFactory
import com.moin.currency_converter.data.local.LocalConvertedCurrency
import com.moin.currency_converter.domain.CurrencyViewModel
import com.squareup.sqldelight.db.SqlDriver
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.JsonObject
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
class MainScreenKtTest: TestCase() {

    private val testDispatcher = StandardTestDispatcher()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val composeTestRule  = createComposeRule()

    private lateinit var viewModel: CurrencyViewModel
    private lateinit var sqlDriver: SqlDriver


    @Before
    public override fun setUp() {
        super.setUp()
        val context = ApplicationProvider.getApplicationContext<Context>()
        sqlDriver = DatabaseDriverFactory(context).createDriver()
        Dispatchers.setMain(testDispatcher)



    }

    @After
    public override fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun mainScreen() = kotlinx.coroutines.test.runTest {
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

        composeTestRule.setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                    MainViewAndroid( sqlDriver)

                }
            }
        }
        testDispatcher.scheduler.advanceUntilIdle()
        composeTestRule.awaitIdle()
        composeTestRule.onNode(hasText("abcd")).assertIsNotDisplayed()
    }

}