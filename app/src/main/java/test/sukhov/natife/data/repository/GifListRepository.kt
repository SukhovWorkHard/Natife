package test.sukhov.natife.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import test.sukhov.natife.data.models.mapper.GifListMapper
import test.sukhov.natife.data.network.APIService
import test.sukhov.natife.data.paggingsources.GifListPagingSource
import test.sukhov.natife.data.paggingsources.NETWORK_PAGE_SIZE
import test.sukhov.natife.ui.models.GifItemResponse

interface GifListRepository {
    suspend fun getGifList(): LiveData<PagingData<GifItemResponse>>
}

class GifRepositoryImpl(
    private val service: APIService,
    private val mapper: GifListMapper
) : GifListRepository {

    override suspend fun getGifList(): LiveData<PagingData<GifItemResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                GifListPagingSource(service, mapper)
            }
        ).liveData
    }
}