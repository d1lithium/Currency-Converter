package com.moin.currency_converter.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.domain.CurrencyViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MainScreen(viewModel: CurrencyViewModel) {

    val state = viewModel.state.collectAsState()
    var inputText by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(
            modifier = Modifier.padding(16.dp).height(60.dp).fillMaxSize(),
            value = inputText,
            onValueChange = {
                inputText = it
            },
            label = { Text(text = "Input Amount") },
            placeholder = { Text(text = "#######.##") },
            singleLine = true

        )

    }
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(64.dp))

        when (val result = state.value) {
            is CurrencyListState.Error -> {
                Text(text = "Error: ${result.message}")
            }
            is CurrencyListState.Loading -> {
                CircularProgressIndicator()

            }
            is CurrencyListState.Success -> {
                Box(
                    modifier = Modifier.align(Alignment.End).width(150.dp)
                ) {
                    CreateDropDownView(result.currencies)
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Success: ${result}}")
            }

        }
    }
}

@Composable
fun CreateDropDownView(result: List<Currency>) {
    val currencyListItems = result.map { it.code }
    var expanded by remember { mutableStateOf(false) }
    val selectedItem by remember { mutableStateOf(currencyListItems[0]) }
    DropDownView(
        modifier = Modifier.fillMaxSize(),
        expanded = expanded,
        listItems = currencyListItems,
        selectedItem = selectedItem
    )
}


