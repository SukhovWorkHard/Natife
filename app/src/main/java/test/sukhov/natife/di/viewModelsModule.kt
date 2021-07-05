package test.sukhov.natife.di

import org.koin.dsl.module
import test.sukhov.natife.ui.giflist.GifListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import test.sukhov.natife.ui.gifdetail.GifDetailViewModel

val viewModelsModule = module {
    viewModel { GifListViewModel(get()) }
    viewModel { GifDetailViewModel(get()) }
}