package com.moin.currency_converter.domain

import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.data.remote.OpenExchangeAPIImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel {
   private val viewModelScope = CoroutineScope(Dispatchers.Main)
   private val exchangeAPI = OpenExchangeAPIImpl()
   val state = MutableStateFlow<CurrencyListState>(CurrencyListState.Loading)

    init {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val currJsonObj = exchangeAPI.getCurrencies()
                val currencyList = ArrayList<Currency>()
                for(i in currJsonObj){
                    currencyList.add(Currency(i.key,i.value.toString()))
                }
                state.emit(
                    CurrencyListState.Success(currencyList)
                )
            }
            catch (e: Exception){
                e.printStackTrace()
                CurrencyListState.Error(e.message?: "something went wrong")
            }


        }
    }
}