package com.moin.currency_converter

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.moin.currency_converter.presentation.commonView
import com.squareup.sqldelight.db.SqlDriver

@Composable
fun MainViewAndroid(sqlDriver: SqlDriver){
   // CurrencyConverterTheme {
        commonView(sqlDriver = sqlDriver)
 //   }
}




