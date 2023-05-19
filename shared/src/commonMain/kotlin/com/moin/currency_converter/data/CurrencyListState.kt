package com.moin.currency_converter.data

sealed interface CurrencyListState{
    object Loading: CurrencyListState
    data class Error(val message: String): CurrencyListState
    data class Success(val currencies: List<Currency>):CurrencyListState
}