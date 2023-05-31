package com.moin.currency_converter

import androidx.compose.runtime.Composable
import com.moin.currency_converter.presentation.commonView
import com.squareup.sqldelight.db.SqlDriver
import dev.tmapps.konnection.Konnection

@Composable
fun MainViewAndroid(sqlDriver: SqlDriver, konnection: Konnection){
   // CurrencyConverterTheme {
        commonView(sqlDriver = sqlDriver, konnection = konnection)
 //   }
}




