package com.moin.currency_converter.data.remote

import com.moin.currency_converter.data.Currency
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.request
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.httpsredirect.HttpsRedirect
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

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
            encodedParameters.append("app_id","f213dc6df7b34824b622d4684cfe8412")
            encodedParameters.append("base","USD")
            encodedParameters.append("symbols","AED,GBP,EUR,FKP,GEL")
            encodedParameters.append("prettyprint", false.toString())
            encodedParameters.append("show_alternative", false.toString())
            encodedPath = path
        }
    }

}