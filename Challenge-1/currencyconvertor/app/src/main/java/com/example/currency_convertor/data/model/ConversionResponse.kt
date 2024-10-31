package com.example.currency_convertor.data.model

data class ConversionResponse(
    val result: String,                       // "success" or "error"
    val documentation: String?,
    val terms_of_use: String?,
    val time_last_update_unix: Long?,
    val time_last_update_utc: String?,
    val time_next_update_unix: Long?,
    val time_next_update_utc: String?,
    val base_code: String?,                   // Base currency code
    val target_code: String?,                 // Target currency code
    val conversion_rate: Double?,             // Conversion rate between the two currencies
    val conversion_result: String?,           // Result of converting the specified amount
    val error_type: String?                   // "unsupported-code", "malformed-request", etc.
)
