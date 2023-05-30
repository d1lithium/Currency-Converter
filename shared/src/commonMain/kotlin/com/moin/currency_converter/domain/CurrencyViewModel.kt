package com.moin.currency_converter.domain

import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import com.moin.currency_converter.CCDatabase
import com.moin.currency_converter.DateTimeUtil
import com.moin.currency_converter.data.ConvertedCurrency
import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyGridState
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.data.local.LocalCCDataSourceImpl
import com.moin.currency_converter.data.local.LocalConvertedCurrency
import com.moin.currency_converter.data.remote.OpenExchangeAPIImpl
import com.squareup.sqldelight.db.SqlDriver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.json.JsonObject
import kotlin.math.roundToInt

class CurrencyViewModel(sqlDriver: SqlDriver) {
    private val viewModelScope = CoroutineScope(Dispatchers.Main)
    var exchangeAPI = OpenExchangeAPIImpl()
    var localCCDataSource = LocalCCDataSourceImpl(CCDatabase(sqlDriver))
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
                if (localCCList.isEmpty()) {
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
        return diff >= 1800000
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
            val multiplier = if (base.toUpperCase(Locale.current) == "USD") {
                value.toDouble()
            } else {
                val oneUSDtoBaseValue =
                    localCCList.firstOrNull { it.code == base }?.value_?.toDouble() ?: 0.0
                value.toDouble() / oneUSDtoBaseValue
            }

            for (i in localCCList) {
                val fullValue = (i.value_.toDouble() * multiplier).toString()
                val resultValue = if (fullValue.contains(".")) {
                    val roundedValue = fullValue.substringBefore('.') +
                            "." +
                            fullValue.substringAfter('.').take(2)
                    if (fullValue.contains("E")) fullValue else roundedValue
                } else {
                    fullValue
                }
                convCurrencyList.add(ConvertedCurrency(i.code, "", resultValue))
            }
        } else {
            for (i in localCCList) {
                convCurrencyList.add(
                    ConvertedCurrency(
                        i.code,
                        "",
                        (i.value_.toDouble() * 0.00).roundToInt().toString()
                    )
                )
            }
        }

        return convCurrencyList
    }



}
