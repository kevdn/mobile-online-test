package com.example.currency_convertor.ui.convert

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.currency_convertor.R
import com.example.currency_convertor.utils.loadCurrencies
import org.koin.androidx.viewmodel.ext.android.viewModel

class ConvertFragment : Fragment() {

    private val viewModel: ConvertViewModel by viewModel()

    private lateinit var baseCurrencyAutocomplete: AutoCompleteTextView
    private lateinit var targetCurrencyAutocomplete: AutoCompleteTextView
    private lateinit var amountInput: EditText
    private lateinit var convertButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var errorTextView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_convert, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load currency list for dropdown
        val currencies = loadCurrencies(requireContext())
        val currencyNames = currencies.values.map { "${it.code} - ${it.name}" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, currencyNames)

        baseCurrencyAutocomplete = view.findViewById(R.id.base_currency_autocomplete)
        targetCurrencyAutocomplete = view.findViewById(R.id.target_currency_autocomplete)
        amountInput = view.findViewById(R.id.amount_input)
        convertButton = view.findViewById(R.id.convert_button)
        resultTextView = view.findViewById(R.id.result_text_view)
        errorTextView = view.findViewById(R.id.error_text_view)

        baseCurrencyAutocomplete.setAdapter(adapter)
        targetCurrencyAutocomplete.setAdapter(adapter)

        // Convert button click listener
        convertButton.setOnClickListener {
            val fromCurrency = baseCurrencyAutocomplete.text.toString().split(" - ")[0]  // Extract code
            val toCurrency = targetCurrencyAutocomplete.text.toString().split(" - ")[0]  // Extract code
            val amount = amountInput.text.toString().toDoubleOrNull()

            // Check for missing base or target currency
            if (fromCurrency.isBlank()) {
                errorTextView.text = "Please select a base currency"
                return@setOnClickListener
            }
            if (toCurrency.isBlank()) {
                errorTextView.text = "Please select a target currency"
                return@setOnClickListener
            }

            // Check for valid amount
            if (amount == null || amount <= 0) {
                errorTextView.text = "Please enter a valid amount"
                return@setOnClickListener
            }

            // If all inputs are valid, proceed with conversion
            errorTextView.text = ""
            viewModel.convertCurrency(fromCurrency, toCurrency, amount)
        }


        // Observers for conversion result and error
        viewModel.conversionResult.observe(viewLifecycleOwner) { formattedResult ->
            // Get the symbol for the target currency
            val targetCurrency = targetCurrencyAutocomplete.text.toString().split(" - ")[0]
            val targetSymbol = currencies[targetCurrency]?.symbol ?: ""

            // Display result with the target currency symbol
            resultTextView.text = "Conversion Result: $targetSymbol $formattedResult"
            errorTextView.text = ""
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorTextView.text = errorMessage
        }
    }
}
