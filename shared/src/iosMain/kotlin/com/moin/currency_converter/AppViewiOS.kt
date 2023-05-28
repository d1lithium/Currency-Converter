package com.moin.currency_converter

import androidx.compose.runtime.Composable
import com.moin.currency_converter.presentation.commonView
import com.squareup.sqldelight.db.SqlDriver

@Composable
internal fun AppViewiOS(sqlDriver: SqlDriver){
    commonView(sqlDriver = sqlDriver)
}
