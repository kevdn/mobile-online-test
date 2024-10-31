package com.example.currency_convertor.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_convertor.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.io.File
import kotlinx.serialization.encodeToString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class DashboardViewModel(
    private val repository: CurrencyRepository,
    private val context: Context
) : ViewModel() {

    private val _conversionRates = MutableLiveData<MutableList<Pair<String, Double>>>()
    val conversionRates: LiveData<MutableList<Pair<String, Double>>> get() = _conversionRates

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    init {
        _conversionRates.value = mutableListOf()
        loadConversionRates() // Load saved rates when ViewModel is created
    }

    fun getConversionRate(baseCurrency: String, targetCurrency: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchLatestRates(baseCurrency)
                val rate = response.conversion_rates?.get(targetCurrency) ?: 0.0
                _conversionRates.value?.add(Pair("$baseCurrency to $targetCurrency", rate))
                saveConversionRates() // Save to file after adding
                _conversionRates.value = _conversionRates.value
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun removeRate(index: Int) {
        _conversionRates.value?.removeAt(index)
        saveConversionRates() // Save to file after removing
        _conversionRates.value = _conversionRates.value
    }

    fun updateConversionRate(baseCurrency: String, targetCurrency: String) {
        viewModelScope.launch {
            try {
                val response = repository.fetchLatestRates(baseCurrency)
                val rate = response.conversion_rates?.get(targetCurrency) ?: 0.0
                val updatedRates = _conversionRates.value?.map {
                    if (it.first == "$baseCurrency to $targetCurrency") {
                        Pair("$baseCurrency to $targetCurrency", rate)
                    } else {
                        it
                    }
                }?.toMutableList()
                _conversionRates.postValue(updatedRates)
                saveConversionRates() // Save after updating
            } catch (e: Exception) {
                _error.postValue(e.message)
            }
        }
    }

    private fun saveConversionRates() {
        val file = File(context.filesDir, "conversion_rates.json")
        val jsonData = Json.encodeToString(_conversionRates.value ?: listOf())
        file.writeText(jsonData)
    }

    private fun loadConversionRates() {
        val file = File(context.filesDir, "conversion_rates.json")
        if (file.exists()) {
            val jsonData = file.readText()
            _conversionRates.value = Json.decodeFromString(jsonData)
        }
    }
}

