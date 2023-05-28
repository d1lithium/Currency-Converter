package com.moin.currency_converter.data.local

import kotlinx.datetime.LocalDateTime

public data class LocalConvertedCurrency(
public val id: Long,
public val base: String,
public val code: String,
public val name: String,
public val value_: String,
public val created: LocalDateTime,
)
