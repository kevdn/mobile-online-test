package com.example.currency_convertor.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rates")
data class CurrencyRate(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val baseCurrency: String,
    val targetCurrency: String,
    val rate: Double
)
