package com.moin.currency_converter

import androidx.compose.ui.window.Application
import platform.UIKit.UIViewController

fun MainViewiOSController(): UIViewController =
    Application(title = "Currency-Converter") {
        AppViewiOS()
    }