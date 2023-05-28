package com.moin.currency_converter.presentation

import androidx.compose.foundation.clickable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import platform.darwin.VM_MEMORY_STACK


@Composable
actual fun DropDownView(
    modifier: Modifier,
    expanded: Boolean,
    listItems: List<String>,
    selectedItem: String
): Pair<Boolean,String> {
    var mutableExpanded  by remember { mutableStateOf(expanded) }



 return Pair(mutableExpanded,"optionSelected")
}




