package test.sukhov.natife.di

import org.koin.dsl.module
import test.sukhov.natife.data.network.RetrofitProvider

private const val BASE_URL = "https://api.giphy.com/v1/"

val networkModule = module {
    single { RetrofitProvider(get(), BASE_URL).provide() }
}