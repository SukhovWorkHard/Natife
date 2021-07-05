package test.sukhov.natife

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import test.sukhov.natife.di.*

class NatifeApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@NatifeApplication)
            modules(appModule, networkModule, repoModule, apiServicesModule, viewModelsModule, mappersModule)
        }
    }
}