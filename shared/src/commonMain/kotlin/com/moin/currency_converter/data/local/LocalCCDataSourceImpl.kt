package com.moin.currency_converter.data.local

import com.moin.currency_converter.DateTimeUtil
import com.moin.currency_converter.CCDatabase

class LocalCCDataSourceImpl(db: CCDatabase): LocalCCDataSource{
     private val queries = db.cCQueries
    override suspend fun insertCCRow(convertedcurrency: LocalConvertedCurrency) {
    queries.insertCC(
        base = convertedcurrency.base,
        code = convertedcurrency.code,
        name = convertedcurrency.name,
        value_ = convertedcurrency.value_,
        created = DateTimeUtil.toEpochMillis(convertedcurrency.created))
     }

    override suspend fun getAllCCRows(): List<LocalConvertedCurrency> {
        return  queries.getAllCCRows().
        executeAsList().
        map { it.toLocalConvertedCurrency() }
    }

    override suspend fun getCCbyCode(code: String): LocalConvertedCurrency? {
        return queries.getCCbyCode(code = code).executeAsOneOrNull()?.toLocalConvertedCurrency()
    }

    override suspend fun deleteCCRowById(id: Long) {
         queries.deleteCCbyId(id = id)
    }

    override suspend fun deleteCCRowByCode(code: String) {
        queries.deleteCCbyCode(code = code)
    }
    override suspend fun deleteAllCCRows() {
        queries.deleteAllCCRows()
    }





}


