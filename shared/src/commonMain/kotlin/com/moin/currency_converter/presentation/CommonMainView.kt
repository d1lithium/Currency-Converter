package com.moin.currency_converter.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.moin.currency_converter.domain.CurrencyViewModel
import com.moin.currency_converter.getPlatform
import com.moin.currency_converter.style.Palette
import com.squareup.sqldelight.db.SqlDriver

@Composable
fun commonView(sqlDriver: SqlDriver){
    if (getPlatform().name == "android"){

    }
    val viewModel = CurrencyViewModel(sqlDriver = sqlDriver)
    MaterialTheme (
        MaterialTheme.colors.copy(
            primary = Palette.DarkBlue
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