package com.moin.currency_converter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import platform.UIKit.UIViewController


fun MainViewiOSController(): UIViewController =
    Application(title = "Currency-Converter") {
        Column {
            // To skip upper part of screen.
            Spacer(modifier = Modifier.height(40.dp))
            AppViewiOS()
        }
    }