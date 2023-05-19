package com.moin.currency_converter.data

import kotlinx.serialization.Serializable

@Serializable
data class Currency(
    val code: String,
    var name: String
    )
