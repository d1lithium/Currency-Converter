package com.moin.currency_converter

import androidx.compose.runtime.Composable
import com.moin.currency_converter.presentation.PreviewDropdownMenu
import com.moin.currency_converter.presentation.commonView
import com.squareup.sqldelight.db.SqlDriver
import dev.tmapps.konnection.Konnection

@Composable
internal fun AppViewiOS(sqlDriver: SqlDriver,konnection: Konnection){
    commonView(sqlDriver = sqlDriver, konnection = konnection)
}
