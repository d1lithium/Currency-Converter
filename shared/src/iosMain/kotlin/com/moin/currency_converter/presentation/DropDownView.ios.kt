package com.moin.currency_converter.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp


@Composable
actual fun DropDownView(
    modifier: Modifier,
    expanded: Boolean,
    listItems: List<String>,
    selectedItem: String
):Pair<Boolean,String> {
    var mutableExpanded  by remember { mutableStateOf(expanded)}
    var selectedItem by remember { mutableStateOf(selectedItem) }



    Box {
        ClickableText(
            text = buildAnnotatedString {
                if (selectedItem == null) {
                    withStyle(SpanStyle(color = Color.Gray)) {
                        append("Select currency")
                    }
                } else {
                    append(selectedItem!!)
                }
            },
            onClick = {
                mutableExpanded = !mutableExpanded
            },
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        Icon(
            imageVector = if (mutableExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
            contentDescription = "Toggle Dropdown",
            tint = Color.Black,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp)
        )
    }
    DropdownMenuList(
        options = listItems,
        onSelectedAction = { option ->
            selectedItem = option
            mutableExpanded = false
        },
        mutableExpanded = mutableExpanded
    )
    return Pair(mutableExpanded,selectedItem)
}

@Composable
fun DropdownMenuList(
    options: List<String>,
    onSelectedAction: (String) -> Unit,
    mutableExpanded: Boolean
) {
    if (mutableExpanded) {
        Surface(
            shape = RoundedCornerShape(4.dp),
            elevation = 2.dp,
            modifier = Modifier
                .padding(top = 4.dp)
                .clip(shape = RoundedCornerShape(4.dp))
                .fillMaxWidth()
                //.preferredHeightIn(maxHeight = 300.dp)
        ) {
            LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
                items(options) { option ->
                    DropdownMenuListRow(
                        option = option,
                        onSelectedAction = onSelectedAction
                    )
                }
            }
        }
    }
}

@Composable
fun DropdownMenuListRow(
    option: String,
    onSelectedAction: (String) -> Unit
) {
    ClickableText(
        text = AnnotatedString(option),
        onClick = { offset ->
            onSelectedAction(option)
        },
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        style = MaterialTheme.typography.body1,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}

@Stable
data class DropdownMenuOption(val option: String)

@Composable
fun PreviewDropdownMenu() {
   var listItems = ArrayList<String>()
    for (i in 0..100){
        listItems.add("items $i")
    }

    val selectedOption = remember { mutableStateOf<DropdownMenuOption?>(null) }

    DropDownView(
        modifier = Modifier.fillMaxSize().width(40.dp),
        listItems = listItems,
        selectedItem = "AED",
        expanded = false


    )
}





