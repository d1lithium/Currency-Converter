package com.moin.currency_converter.domain

import androidx.compose.runtime.CompositionLocalContext
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.moin.currency_converter.CCDatabase
import com.moin.currency_converter.DateTimeUtil
import com.moin.currency_converter.data.ConvertedCurrency
import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyGridState
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.data.local.DatabaseDriverFactory
import com.moin.currency_converter.data.local.LocalCCDataSourceImpl
import com.moin.currency_converter.data.local.LocalConvertedCurrency
import com.moin.currency_converter.data.local.abc
import com.moin.currency_converter.data.remote.OpenExchangeAPIImpl
import com.moin.currency_converter. getPlatform
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonObject
import org.jetbrains.compose.resources.LoadState
import kotlin.math.roundToInt
import kotlin.math.roundToLong

class CurrencyViewModel(sqlDriver: SqlDriver) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    private val exchangeAPI = OpenExchangeAPIImpl()
    private val localCCDataSource = LocalCCDataSourceImpl(CCDatabase(sqlDriver))
    val state = MutableStateFlow<CurrencyListState>(CurrencyListState.Loading)
    val gridState = MutableStateFlow<CurrencyGridState>(CurrencyGridState.Loading)

    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val currJsonObj = exchangeAPI.getCurrencies()
                val currencyList = parseCurrencies(currJsonObj)
                state.emit(CurrencyListState.Success(currencyList))
            } catch (e: Exception) {
                e.printStackTrace()
                CurrencyListState.Error(e.message ?: "something went wrong")
            }
        }
    }

    fun getLatest(base: String, value: String) {
        viewModelScope.launch {
            try {
                val localCCList = localCCDataSource.getAllCCRows()
                if (localCCList.isNullOrEmpty()) {
                    updateLocalCCRows(true)
                } else {
                    val isDataExpired = isDataExpired(localCCList.first().created)
                    if (isDataExpired) {
                        updateLocalCCRows(false)
                    }
                }
                val convCurrencyList = convertCurrencies(localCCList, base, value)
                gridState.emit(CurrencyGridState.Success(convCurrencyList))
            } catch (e: Exception) {
                e.printStackTrace()
                CurrencyGridState.Error(e.message ?: "something went wrong")
            }
        }
    }

    fun loadCCList(): List<LocalConvertedCurrency> {
        var localCCList: List<LocalConvertedCurrency> = emptyList()
        viewModelScope.launch(Dispatchers.Main) {
            try {
                localCCList = localCCDataSource.getAllCCRows()
                if (localCCList.isNullOrEmpty()) {
                    updateLocalCCRows(true)
                } else {
                    val isDataExpired = isDataExpired(localCCList.first().created)
                    if (isDataExpired) {
                        updateLocalCCRows(false)
                    }
                }
                println("historical rates: $localCCList")
            } catch (e: Exception) {
                println("something went wrong: ${e.message}")
            }
        }
        return localCCList
    }

    private suspend fun updateLocalCCRows(isLocalDBEmpty: Boolean) {
        val latestConvObj = exchangeAPI.getHistoricalRates()

        if (!isLocalDBEmpty) {
            localCCDataSource.deleteAllCCRows()
        }

        for (i in latestConvObj) {
            localCCDataSource.insertCCRow(
                LocalConvertedCurrency(
                    0, base = "USD", i.key, "", i.value.toString(), DateTimeUtil.now()
                )
            )
        }
    }

    private fun isDataExpired(created: LocalDateTime): Boolean {
        val diff = DateTimeUtil.toEpochMillis(DateTimeUtil.now()) -
                DateTimeUtil.toEpochMillis(created)
       // return diff >= 1800000
        return diff >= 300000
    }

    private fun parseCurrencies(currJsonObj: JsonObject): List<Currency> {
        val currencyList = mutableListOf<Currency>()
        for (i in currJsonObj) {
            currencyList.add(Currency(i.key, i.value.toString()))
        }
        return currencyList
    }

    private fun convertCurrencies(
        localCCList: List<LocalConvertedCurrency>,
        base: String,
        value: String
    ): List<ConvertedCurrency> {
        val convCurrencyList = mutableListOf<ConvertedCurrency>()
        if (value.isNotEmpty() && value.isNotBlank()) {
            val cCList = localCCList
            value.let {
                if (base.toUpperCase(Locale.current) == "USD") {
                    for (i in cCList) {
                        val fullValue = (i.value_.toDouble() * value.toDouble()).toString()
                        val resultValue = if (fullValue.contains(".")) {
                            if (fullValue.substringAfter('.').length > 2) {
                                val valueA = fullValue.substringBefore('.')
                                val valueB = fullValue.substringAfter('.').substring(0, 2)
                                if (fullValue.contains("E")) {
                                    fullValue
                                } else {
                                    "$valueA.$valueB"
                                }
                            } else {
                                fullValue
                            }
                        } else {
                            fullValue
                        }
                        convCurrencyList.add(ConvertedCurrency(i.code, "", resultValue))
                    }
                } else {
                    val oneUSDtoBaseValue = cCList.filter { it.code == base }.first()
                    val one_usd_to_base = oneUSDtoBaseValue.value_.toDouble()
                    for (i in cCList) {
                        val one_unit_base_value = i.value_.toDouble() / one_usd_to_base
                        var fullValue = (one_unit_base_value * value.toDouble()).toString()
                        var resultValue = ""
                        if (fullValue.contains(".")) {
                            if (fullValue.substringAfter('.').length > 2) {
                                val valueA = fullValue.substringBefore('.')
                                val valueB = fullValue.substringAfter('.').substring(0, 2)
                                if (fullValue.contains("E")) {
                                    resultValue = fullValue
                                } else {
                                    resultValue = "$valueA.$valueB"
                                }
                            } else {
                                resultValue = fullValue
                            }
                        } else {
                            resultValue = fullValue
                        }
                        convCurrencyList.add(ConvertedCurrency(i.code, "", resultValue))
                    }
                }
            }
        } else {
            for (i in localCCList) {
                convCurrencyList.add(
                    ConvertedCurrency(
                        i.code,
                        "",
                        (i.value_.toString().toDouble() * 0.00).roundToInt().toString()
                    )
                )
            }
        }
        return convCurrencyList
    }
}
