package com.moin.currency_converter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.moin.currency_converter.StringtoBooleanStringPair
import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.domain.CurrencyViewModel
import com.moin.currency_converter.getFilteredCurrencyList
import com.moin.currency_converter.getRandomColor
import com.moin.currency_converter.style.Palette


@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun MainScreen(viewModel: CurrencyViewModel) {

    val state = viewModel.state.collectAsState()
    var inputText by remember { mutableStateOf(TextFieldValue("")) }
    var dropDownPair by remember { mutableStateOf(Pair(true, "")) }
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
                    dropDownPair =
                        StringtoBooleanStringPair(CreateDropDownView(result.currencies).toString())
                }
                Spacer(modifier = Modifier.height(16.dp))
                // Text(text = "Success: ${result}}")
                  val updatedCurrencyList = getFilteredCurrencyList(result.currencies,dropDownPair.second)
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(100.dp),
                        content = {
                            items(updatedCurrencyList.size) { i ->
                                Box(
                                    modifier = Modifier.padding(5.dp)
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(5.dp))
                                        .background(androidx.compose.ui.graphics.Color.Magenta),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = updatedCurrencyList.get(i).code)
                                }
                            }
                        }
                    )
            }

        }
    }
}

@Composable
internal fun CreateDropDownView(result: List<Currency>): Pair<Boolean, String> {
    val currencyListItems = result.map { it.code }
    val expanded by remember { mutableStateOf(false) }
    val selectedItem by remember { mutableStateOf(currencyListItems[0]) }
    val (isExpanded, selectedCurrency) = DropDownView(
        modifier = Modifier.fillMaxSize(),
        expanded = expanded,
        listItems = currencyListItems,
        selectedItem = selectedItem
    )
    return Pair(isExpanded, selectedCurrency);


}


