package test.sukhov.natife.di

import org.koin.dsl.module
import retrofit2.Retrofit
import test.sukhov.natife.data.network.APIService

val apiServicesModule = module {
    single<APIService> { get<Retrofit>().create(APIService::class.java) }
}