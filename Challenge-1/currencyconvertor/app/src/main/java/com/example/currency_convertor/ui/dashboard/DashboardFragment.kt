package com.example.currency_convertor.ui.dashboard

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.example.currency_convertor.R
import com.example.currency_convertor.data.local.CurrencyRate
import com.example.currency_convertor.data.model.Currency
import com.example.currency_convertor.utils.loadCurrencies

class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModel()

    private lateinit var baseCurrencyAutocomplete: AutoCompleteTextView
    private lateinit var targetCurrencyAutocomplete: AutoCompleteTextView
    private lateinit var getRateButton: Button
    private lateinit var errorTextView: TextView
    private lateinit var ratesRecyclerView: RecyclerView
    private lateinit var ratesAdapter: RateAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currencies = loadCurrencies(requireContext())
        val currencyNames = currencies.values.map { "${it.code} - ${it.name}" }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, currencyNames)

        baseCurrencyAutocomplete = view.findViewById(R.id.base_currency_autocomplete)
        targetCurrencyAutocomplete = view.findViewById(R.id.target_currency_autocomplete)
        getRateButton = view.findViewById(R.id.get_rate_button)
        errorTextView = view.findViewById(R.id.error_text_view)
        ratesRecyclerView = view.findViewById(R.id.rates_recycler_view)

        // Initialize RecyclerView
        ratesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        ratesAdapter = RateAdapter(mutableListOf(), currencies)
        ratesRecyclerView.adapter = ratesAdapter

        baseCurrencyAutocomplete.setAdapter(adapter)
        targetCurrencyAutocomplete.setAdapter(adapter)

        getRateButton.setOnClickListener {
            val baseCurrency = baseCurrencyAutocomplete.text.toString().split(" - ")[0]
            val targetCurrency = targetCurrencyAutocomplete.text.toString().split(" - ")[0]

            // Check for missing base or target currency
            if (baseCurrency.isBlank()) {
                errorTextView.text = "Please select a base currency"
                return@setOnClickListener
            }
            if (targetCurrency.isBlank()) {
                errorTextView.text = "Please select a target currency"
                return@setOnClickListener
            }

            if (targetCurrency == baseCurrency) {
                errorTextView.text = "Base and target currencies cannot be the same."
                return@setOnClickListener
            }

            // Check if this currency pair already exists
            val pairExists = ratesAdapter.getRates().any { it.baseCurrency == baseCurrency && it.targetCurrency == targetCurrency }
            if (pairExists) {
                errorTextView.text = "This currency pair already exists in the list."
            } else {
                errorTextView.text = ""  // Clear any existing error
                viewModel.getConversionRate(baseCurrency, targetCurrency)
            }
        }


        viewModel.conversionRates.observe(viewLifecycleOwner) { rates ->
            val currencyRates = rates.map {
                val (currencies, rate) = it
                val (baseCurrency, targetCurrency) = currencies.split(" to ")
                CurrencyRate(baseCurrency = baseCurrency, targetCurrency = targetCurrency, rate = rate)
            }
            ratesAdapter.updateRates(currencyRates)
            errorTextView.text = ""
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            errorTextView.text = errorMessage
        }
    }

    private inner class RateAdapter(
        private var rates: MutableList<CurrencyRate>,
        private val currencies: Map<String, Currency>
    ) : RecyclerView.Adapter<RateAdapter.RateViewHolder>() {

        inner class RateViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val rateTextView: TextView = view.findViewById(R.id.rate_text_view)
            val updateButton: Button = view.findViewById(R.id.update_button)
            val deleteButton: Button = view.findViewById(R.id.delete_button)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rate, parent, false)
            return RateViewHolder(view)
        }

        @SuppressLint("SetTextI18n")
        override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
            val rate = rates[position]
            val baseSymbol = currencies[rate.baseCurrency]?.symbol ?: ""
            val targetSymbol = currencies[rate.targetCurrency]?.symbol ?: ""
            holder.rateTextView.text = "${rate.baseCurrency} ($baseSymbol) to ${rate.targetCurrency} ($targetSymbol): ${rate.rate}"

            holder.updateButton.setOnClickListener {
                viewModel.updateConversionRate(rate.baseCurrency, rate.targetCurrency)
            }

            holder.deleteButton.setOnClickListener {
                viewModel.removeRate(position)
            }
        }

        override fun getItemCount(): Int = rates.size

        fun updateRates(newRates: List<CurrencyRate>) {
            rates.clear()
            rates.addAll(newRates)
            notifyDataSetChanged()
        }

        fun getRates(): List<CurrencyRate> = rates

    }
}
