package com.moin.currency_converter.data.local

import database.Convertedcurrency
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Convertedcurrency.toLocalConvertedCurrency(): LocalConvertedCurrency {
    return LocalConvertedCurrency(
        id = id,
        base = base,
        code = code,
        value_ = value_,
        name = name,
        created = Instant
            .fromEpochMilliseconds(created)
            .toLocalDateTime(TimeZone.currentSystemDefault())
    )
}
