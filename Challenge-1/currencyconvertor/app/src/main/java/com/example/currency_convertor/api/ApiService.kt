package com.example.currency_convertor.api


import com.example.currency_convertor.data.model.ConversionResponse
import com.example.currency_convertor.data.model.CurrencyRateResponse
import retrofit2.http.GET
import retrofit2.http.Path
import com.example.currency_convertor.utils.Constants

interface ApiService {

    @GET("${Constants.BASE_URL}/${Constants.API_KEY}/pair/{from}/{to}/{amount}")
    suspend fun convertCurrency(
        @Path("from") from: String,                 // From currency code, e.g., "EUR"
        @Path("to") to: String,                     // To currency code, e.g., "GBP"
        @Path("amount") amount: Double              // Optional amount for conversion
    ): ConversionResponse

    @GET("${Constants.BASE_URL}/${Constants.API_KEY}/latest/{base}")
    suspend fun getLatestRates(
        @Path("base") base: String
    ): CurrencyRateResponse

}
