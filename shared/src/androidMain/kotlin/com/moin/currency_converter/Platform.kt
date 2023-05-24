package com.moin.currency_converter

import androidx.compose.runtime.Composable
import androidx.compose.material.*
import androidx.compose.material.ExperimentalMaterialApi


class AndroidPlatform : Platform {
    override val name: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun abc(){
    ExposedDropdownMenuBox(expanded = true, onExpandedChange = {it}) {}
}
