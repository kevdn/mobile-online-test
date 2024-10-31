package com.example.currency_convertor.ui.convert

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currency_convertor.data.repository.CurrencyRepository
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class ConvertViewModel(private val repository: CurrencyRepository) : ViewModel() {

    private val _conversionResult = MutableLiveData<String>()
    val conversionResult: LiveData<String> = _conversionResult

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun convertCurrency(from: String, to: String, amount: Double) {
        viewModelScope.launch {
            try {
                val result = repository.convertCurrency(from, to, amount)

                // Safely parse and format the conversion result
                val formattedResult = result.conversion_result?.let { conversionResult ->
                    conversionResult.toDoubleOrNull()?.let {
                        DecimalFormat("#,###.00").format(it) // Format if valid number
                    } ?: "Invalid number"  // Fallback for non-numeric values
                } ?: "Conversion failed" // Fallback if conversion_result is null

                _conversionResult.value = formattedResult
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            }
        }
    }
}
