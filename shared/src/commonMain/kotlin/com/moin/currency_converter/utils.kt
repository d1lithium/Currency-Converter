package com.moin.currency_converter

import androidx.compose.ui.graphics.Color
import com.moin.currency_converter.data.Currency
import kotlin.random.Random


internal  fun StringtoBooleanStringPair(str: String): Pair<Boolean,String>{
    return Pair(str.substringBefore(',').toBoolean(), str.substringAfter(',').removeSuffix(")").trim() )
}

internal fun getRandomColor(): Color {
return Color.Unspecified
}
internal fun getFilteredCurrencyList(list: List<Currency>, value: String): List<Currency> {
   return list.filter { it.code != value }
}