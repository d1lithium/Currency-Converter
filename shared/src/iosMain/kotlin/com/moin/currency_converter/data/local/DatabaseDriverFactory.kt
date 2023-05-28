package com.moin.currency_converter.data.local


import com.moin.currency_converter.CCDatabase
import com.moin.currency_converter.db_name
import com.squareup.sqldelight.db.SqlDriver
import com.squareup.sqldelight.drivers.native.NativeSqliteDriver

actual class DatabaseDriverFactory {
    actual fun createDriver(): SqlDriver {
        return NativeSqliteDriver(CCDatabase.Schema, db_name)
    }

}
