package com.moin.currency_converter.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moin.currency_converter.MainViewAndroid
import com.moin.currency_converter.data.Currency
import com.moin.currency_converter.data.CurrencyListState
import com.moin.currency_converter.data.local.DatabaseDriverFactory
import com.moin.currency_converter.domain.CurrencyViewModel
import com.moin.currency_converter.presentation.*

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColors(
            primary = Color(0xFFBB86FC),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    } else {
        lightColors(
            primary = Color(0xFF6200EE),
            primaryVariant = Color(0xFF3700B3),
            secondary = Color(0xFF03DAC5)
        )
    }
    val typography = Typography(
        body1 = TextStyle(
            fontFamily = FontFamily.Default,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        )
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.White
                ) {
                   MainViewAndroid( DatabaseDriverFactory(applicationContext).createDriver())
                }
            }
        }
    }
}

@Composable
fun Greeting(text: String) {
    Text(text = text)
}

@Preview
@Composable
fun DefaultPreview() {
    MaterialTheme {
     //   commonView()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun CurrencyScreen( viewModel: CurrencyViewModel){
    val state = viewModel.state.collectAsState()
    Column(modifier = Modifier.padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))

        Spacer(modifier = Modifier.height(16.dp))
        ExposedDropdownMenuBox(expanded = true, onExpandedChange = {it}) {

        }
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

@Composable
fun CurrencySelector(currency: Currency, onCurrencySelected: (Currency) -> Unit) {
    // Implement currency selector UI
}



