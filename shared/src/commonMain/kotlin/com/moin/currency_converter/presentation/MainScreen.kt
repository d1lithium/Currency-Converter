package com.moin.currency_converter.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.domain.CurrencyViewModel


@Composable
internal fun MainScreen(viewModel: CurrencyViewModel){

    val state = viewModel.state.collectAsState()
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        TextField(modifier = Modifier.padding(16.dp).height(60.dp).fillMaxSize(),
            value = inputText,
            onValueChange = {
                inputText = it
            },
            label = {Text(text = "Input Amount")},
            placeholder = {Text(text = "#######.##")},
            singleLine = true

            )

    }
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {

        Spacer(modifier = Modifier.height(64.dp))

        Button(
            onClick = {

            }
        ) {
            Text("Convert")
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (val result = state.value) {
            is CurrencyListState.Success -> {
                Text("Result: ${result.currencies}")
            }
            is CurrencyListState.Error -> {
                Text("Error: ${result.message}")
            }
            is CurrencyListState.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}