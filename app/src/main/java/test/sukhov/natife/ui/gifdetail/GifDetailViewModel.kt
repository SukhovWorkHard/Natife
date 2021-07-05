package test.sukhov.natife.ui.gifdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import test.sukhov.natife.data.repository.GifListRepository
import test.sukhov.natife.ui.base.BaseViewModel
import test.sukhov.natife.ui.models.GifItemResponse

class GifDetailViewModel(private val repository: GifListRepository) : BaseViewModel() {
    private val _gifList = MutableLiveData<PagingData<GifItemResponse>>()

    suspend fun getGifList(): LiveData<PagingData<GifItemResponse>> {
        val response = repository.getGifList().cachedIn(viewModelScope)
        _gifList.value = response.value
        return response
    }
}