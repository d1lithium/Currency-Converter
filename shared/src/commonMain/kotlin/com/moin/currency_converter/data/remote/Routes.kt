package com.moin.currency_converter.data.remote

import com.moin.currency_converter.app_id

object Routes {
    //val BASEURL = "https://openexchangerates.org/api/"
    val BASEURL = "http://192.168.100.11:3000/"
    val latest = "latest.json"
    val currencies ="currencies.json"
    val historical = "historical.json"
    //val historical = "historical/2022-12-13.json?app_id=$app_id"
}