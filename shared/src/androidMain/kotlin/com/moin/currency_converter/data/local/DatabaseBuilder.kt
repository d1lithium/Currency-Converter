package com.moin.currency_converter.data.local

import android.app.Application
import android.content.Context

actual fun abc():String{
    return "Abc"
}
fun getAppContext(application: Application):Context{
    return application.applicationContext
}