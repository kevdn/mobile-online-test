package com.example.currency_convertor.data.model

data class CurrencyRateResponse(
    val result: String,
    val documentation: String?,
    val terms_of_use: String?,
    val time_last_update_unix: Long?,
    val time_last_update_utc: String?,
    val time_next_update_unix: Long?,
    val time_next_update_utc: String?,
    val base_code: String?,
    val conversion_rates: Map<String, Double>?,
    val error_type: String?                    // "unsupported-code", "malformed-request", etc.
) {
    // Function to get the rate for a specific target currency
    fun getRateForCurrency(targetCurrency: String): Double? {
        return conversion_rates?.get(targetCurrency)
    }
}
