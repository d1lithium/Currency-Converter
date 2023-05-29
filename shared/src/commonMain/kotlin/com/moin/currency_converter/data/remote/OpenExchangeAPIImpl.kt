package com.moin.currency_converter.data.remote

import com.moin.currency_converter.app_id
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.JsonObject
class OpenExchangeAPIImpl:OpenExchangeAPI {

    override suspend fun getCurrencies(): JsonObject {
       return client.get {
           openexchange(Routes.currencies)
       }.body()
    }

    override suspend fun getLatestRates(baseCurrency: String): JsonObject {
        return client.get {
            openexchange(Routes.latest)
        }.body<JsonObject>()["rates"] as JsonObject
    }

    override suspend fun getHistoricalRates(): JsonObject {
        return client.get {
            openexchange(Routes.historical)
            //openExchangeStatic(Routes.latest)
        }.body<JsonObject>()["rates"] as JsonObject
    }

    private val client = HttpClient {
        expectSuccess = true

        install(HttpTimeout) {
            val timeout = 300000L
            connectTimeoutMillis = timeout
            requestTimeoutMillis = timeout
            socketTimeoutMillis = timeout
        }
        install(ContentNegotiation) {
            json()
        }

    }
    private fun HttpRequestBuilder.openexchange(path:String){
        url {
            takeFrom(Routes.BASEURL)
             encodedPath = path
        }
    }
    private fun HttpRequestBuilder.openExchangeStatic(path:String){
        url {
            takeFrom("https://openexchangerates.org/api/")
            //takeFrom("http://192.168.100.11:3000/")
            parameters.append("app_id", app_id)
            parameters.append("base","USD")
            parameters.append("symbols","AED,GBP,EUR,FKP,GEL")
            parameters.append("prettyprint", false.toString())
            parameters.append("show_alternative", false.toString())
            encodedPath = path
        }
    }

}