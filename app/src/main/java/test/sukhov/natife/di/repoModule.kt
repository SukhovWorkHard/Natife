package test.sukhov.natife.di

import org.koin.dsl.module
import test.sukhov.natife.data.repository.GifListRepository
import test.sukhov.natife.data.repository.GifRepositoryImpl

val repoModule = module {
    single<GifListRepository> { GifRepositoryImpl(get(), get()) }
}