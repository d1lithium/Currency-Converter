package com.moin.currency_converter.data

sealed interface CurrencyGridState{
    object Loading: CurrencyGridState
    data class Error(val message: String): CurrencyGridState
    data class Success(val list: List<ConvertedCurrency>): CurrencyGridState
}