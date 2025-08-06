package com.bksd.brandapp

import android.app.Application
import com.bksd.brandapp.di.appModules
import com.bksd.brandapp.di.networkModule
import com.bksd.brandapp.di.useCaseModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class BrandApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@BrandApp)
            modules(appModules, networkModule, useCaseModule)

        }
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }
}