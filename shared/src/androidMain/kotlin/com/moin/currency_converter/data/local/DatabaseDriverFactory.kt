package com.moin.currency_converter.data.local

import android.content.Context
import com.moin.currency_converter.CCDatabase
import com.moin.currency_converter.db_name
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver

actual class DatabaseDriverFactory(private val context: Context) {
    actual fun createDriver(): SqlDriver {
        return AndroidSqliteDriver(CCDatabase.Schema, context, db_name)
    }


}

