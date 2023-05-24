package com.moin.currency_converter.data.remote

import com.moin.currency_converter.data.Currency
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject

class OpenExchangeAPIImpl:OpenExchangeAPI {

    override suspend fun getCurrencies(): JsonObject {
       return client.get {
           openexchange("currencies.json")
       }.body()
    }

    override suspend fun getLatest(baseCurrency: String): JsonObject {
        return client.post {
            openexchange("latest.json")
        }.body()
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
            //takeFrom("https://openexchangerates.org/api/")
            takeFrom("http://192.168.100.11:3000/")
        encodedPath = path
        }
    }

}