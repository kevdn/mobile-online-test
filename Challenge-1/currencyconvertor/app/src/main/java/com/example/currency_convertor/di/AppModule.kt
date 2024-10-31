package com.example.currency_convertor.di

import com.example.currency_convertor.api.ApiService
import com.example.currency_convertor.data.repository.CurrencyRepository
import com.example.currency_convertor.ui.convert.ConvertViewModel
import com.example.currency_convertor.ui.dashboard.DashboardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.currency_convertor.utils.Constants

val appModule = module {

    // Create Retrofit instance first
    single {
        Retrofit.Builder()
            .baseUrl("https://v6.exchangerate-api.com/v6/${Constants.API_KEY}/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Create ApiService instance after Retrofit
    single { get<Retrofit>().create(ApiService::class.java) }

    // Create CurrencyRepository instance
    single { CurrencyRepository(get()) }

    // Create ViewModels
    viewModel { ConvertViewModel(get()) }
    viewModel { DashboardViewModel(get(), get()) }
}