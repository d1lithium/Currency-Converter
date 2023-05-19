package com.moin.currency_converter.presentation

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import com.moin.currency_converter.domain.CurrencyViewModel
import com.moin.currency_converter.style.Palette

@Composable
internal fun commonView(){
    val viewModel = CurrencyViewModel()
    MaterialTheme (
        MaterialTheme.colors.copy(
            primary = Palette.DarkGray,
            secondary = Palette.LightGray,
        )
    ){
        MainScreen(viewModel)
    }

}