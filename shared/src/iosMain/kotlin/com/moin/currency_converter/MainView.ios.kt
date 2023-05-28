package com.moin.currency_converter

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Application
import com.moin.currency_converter.data.local.DatabaseDriverFactory
import com.squareup.sqldelight.db.SqlDriver
import platform.UIKit.UIViewController


fun MainViewiOSController(): UIViewController =
    Application(title = "Currency-Converter") {
        Column {
            // To skip upper part of screen.
            Spacer(modifier = Modifier.height(40.dp))
            val databaseDriverFactory: DatabaseDriverFactory = DatabaseDriverFactory()
            val sqlDriver: SqlDriver = databaseDriverFactory.createDriver()
            AppViewiOS(sqlDriver = sqlDriver)
        }
    }