package com.moin.currency_converter.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.moin.currency_converter.domain.CurrencyViewModel

@Composable
fun commonView(){
    val viewModel = CurrencyViewModel()
    MaterialTheme (
        MaterialTheme.colors.copy(
            primary = Color.Magenta
        )
    ){
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            MainScreen(viewModel)
        }

    }

}