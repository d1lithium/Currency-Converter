package com.moin.currency_converter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform