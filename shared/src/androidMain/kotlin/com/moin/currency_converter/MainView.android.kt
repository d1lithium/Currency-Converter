package com.moin.currency_converter

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.runtime.Composable
import com.moin.currency_converter.presentation.commonView

@Composable
fun MainViewAndroid(){
    CurrencyConverterTheme {
        commonView()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun dropDownMenu(){
    ExposedDropdownMenuBox(expanded = false, onExpandedChange = {it}) {

    }
}



