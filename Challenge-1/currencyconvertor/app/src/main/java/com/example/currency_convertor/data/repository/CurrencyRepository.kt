package com.example.currency_convertor.data.repository

import com.example.currency_convertor.data.model.ConversionResponse
import com.example.currency_convertor.api.ApiService
import com.example.currency_convertor.data.model.CurrencyRateResponse

class CurrencyRepository(private val apiService: ApiService
) {

    suspend fun fetchLatestRates(base: String): CurrencyRateResponse {
        // Calls the updated API method without symbols
        return apiService.getLatestRates(base)
    }

    suspend fun convertCurrency(from: String, to: String, amount: Double): ConversionResponse {
        // Calls the updated API method with base, target, and amount as path parameters
        return apiService.convertCurrency(from, to, amount)
    }

}

