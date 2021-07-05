package test.sukhov.natife.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import test.sukhov.natife.data.models.GifListJson

interface APIService {
    @GET("gifs/search")
    suspend fun getGifTrending(
        @Query("q") searchKey: String = "stitch",
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): GifListJson
}