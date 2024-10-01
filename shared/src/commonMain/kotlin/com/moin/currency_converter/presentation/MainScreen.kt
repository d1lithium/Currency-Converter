@file:OptIn(ExperimentalComposeUiApi::class)

package com.moin.currency_converter.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moin.currency_converter.StringtoBooleanStringPair
import com.moin.currency_converter.data.ConvertedCurrency
import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyGridState
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.domain.CurrencyViewModel
import com.moin.currency_converter.getFilteredCurrencyList
import com.moin.currency_converter.style.Palette
import kotlin.random.Random


@Composable
internal fun MainScreen(viewModel: CurrencyViewModel) {
   val keyboardCtrlr = LocalSoftwareKeyboardController.current
    val state by viewModel.state.collectAsState()
    val gridState by viewModel.gridState.collectAsState()
    var inputText by remember { mutableStateOf("") }
    val pattern = remember { Regex("^(?:0|[1-9]\\d+|)?(?:.?\\d{0,2})?$") }
    val maxChar = 10
    var dropDownPair by remember { mutableStateOf(Pair(true, "")) }

    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(16.dp)
    ) {
        TextField(
            modifier = Modifier.height(60.dp).fillMaxSize(),
            value = inputText,
            onValueChange = { if (it.matches(pattern) && it.length <= maxChar) inputText = it },
            label = { Text(text = "Input Amount") },
            placeholder = { Text(text = "#######.##") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        )
    }

    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(80.dp))
        when (val result = state) {
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
                        StringtoBooleanStringPair(DropDown(result.currencies).toString())
                    viewModel.getLatest(dropDownPair.second, inputText)
                }
                Spacer(modifier = Modifier.height(16.dp))
                when (val result = gridState) {
                    is CurrencyGridState.Error -> {
                        Text(text = "Error: ${result.message}")
                    }

                    is CurrencyGridState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is CurrencyGridState.Success -> {
                        val updatedCurrencyList =
                            getFilteredCurrencyList(result.list, dropDownPair.second)
                        val rndmClrs = generateRandomColors(updatedCurrencyList.size)
                        LazyVerticalGrid(
                            columns = GridCells.Adaptive(120.dp),
                            content = {
                                items(updatedCurrencyList.zip(rndmClrs)) { (currencyObj, clr) ->
                                    CurrencyTile(currencyObj,clr, keyboardCtrlr)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DropDown(result: List<Currency>): Pair<Boolean, String> {
    val currencyListItems = result.map { it.code }
    val expanded by remember { mutableStateOf(false) }
    val selectedItem by remember { mutableStateOf(currencyListItems[0]) }
    val (isExpanded, selectedCurrency) = DropDownView(
        modifier = Modifier.background(color = Palette.LightBlue).fillMaxSize(),
        expanded = expanded,
        listItems = currencyListItems,
        selectedItem = selectedItem
    )
    return Pair(isExpanded, selectedCurrency)
}

@Composable
fun CurrencyTile(
    currencyObj: ConvertedCurrency,
    clr: Color,
    keyboardCtrlr: SoftwareKeyboardController?
) {
    Box(
        modifier = Modifier.padding(5.dp).aspectRatio(1f).clip(RoundedCornerShape(5.dp))
            .background(clr)
            .clickable { keyboardCtrlr!!.hide() },
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier.padding(5.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = currencyObj.value,
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                fontWeight = FontWeight.Bold,
                text = currencyObj.code,
                style = TextStyle(textDecoration = TextDecoration.Underline)
            )
        }
    }
}

fun generateRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        blue = Random.nextFloat(),
        green = Random.nextFloat(),
        alpha = 1f
    )
}

fun generateRandomColors(count: Int): List<Color>{
   return List(count){ generateRandomColor() }
}

