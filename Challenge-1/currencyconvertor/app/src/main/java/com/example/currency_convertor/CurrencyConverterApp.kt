package com.example.currency_convertor

import android.app.Application
import com.example.currency_convertor.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class CurrencyConverterApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin
        startKoin {
            androidContext(this@CurrencyConverterApp)
            modules(appModule) // Replace with your actual Koin modules
        }
    }
}
