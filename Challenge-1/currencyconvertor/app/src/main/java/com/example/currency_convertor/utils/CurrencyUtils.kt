package com.example.currency_convertor.utils

import android.content.Context
import com.example.currency_convertor.data.model.Currency
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader

fun loadCurrencies(context: Context): Map<String, Currency> {
    val json = context.assets.open("common-currency.json").bufferedReader().use { it.readText() }
    val type = object : TypeToken<Map<String, Currency>>() {}.type
    return Gson().fromJson(json, type)
}

fun getSymbols(currencies: Map<String, Currency>): Map<String, String> {
    return currencies.mapValues { it.value.symbol }
}
