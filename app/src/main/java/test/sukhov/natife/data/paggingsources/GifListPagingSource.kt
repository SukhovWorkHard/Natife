package test.sukhov.natife.data.paggingsources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import test.sukhov.natife.data.models.mapper.GifListMapper
import test.sukhov.natife.data.network.APIService
import test.sukhov.natife.ui.models.GifItemResponse

const val NETWORK_PAGE_SIZE = 25
private const val INITIAL_LOAD_SIZE = 0

class GifListPagingSource(
    private val service: APIService,
    private val mapper: GifListMapper
    ) : PagingSource<Int, GifItemResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifItemResponse> {
        val position = params.key ?: INITIAL_LOAD_SIZE
        val offset = if (params.key != null) ((position - 1) * NETWORK_PAGE_SIZE) + 1 else INITIAL_LOAD_SIZE

        return try {
            val jsonResponse = service.getGifTrending(offset = offset, limit = params.loadSize).data
            val response = mapper.toGifListResponse(jsonResponse)
            val nextKey = if (response.isEmpty()) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, GifItemResponse>): Int? {
        return null
    }
}