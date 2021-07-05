package test.sukhov.natife.di

import org.koin.dsl.module
import test.sukhov.natife.data.models.mapper.GifListMapper

val mappersModule = module {
    single { GifListMapper() }
}