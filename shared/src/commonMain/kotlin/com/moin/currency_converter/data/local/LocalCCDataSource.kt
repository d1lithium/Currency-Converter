package com.moin.currency_converter.data.local

interface LocalCCDataSource {
    suspend fun insertCCRow(convertedcurrency: LocalConvertedCurrency)

    suspend fun getAllCCRows():List<LocalConvertedCurrency>

    suspend fun getCCbyCode(code: String): LocalConvertedCurrency?

    suspend fun deleteCCRowById(id: Long)

    suspend fun deleteCCRowByCode(code: String)

    suspend fun deleteAllCCRows()
}