package com.moin.currency_converter.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.domain.CurrencyViewModel


@Composable
internal fun MainScreen(viewModel: CurrencyViewModel){

    val state = viewModel.state.collectAsState()
    Column(modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                val amount = 12
                if (amount != null) {

                }
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