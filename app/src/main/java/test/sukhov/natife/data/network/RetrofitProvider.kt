package test.sukhov.natife.data.network

import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import test.sukhov.natife.BuildConfig
import test.sukhov.natife.data.models.ApiException
import test.sukhov.natife.data.models.ApiExceptionBody
import test.sukhov.natife.data.models.ErrorJson
import java.util.concurrent.TimeUnit

class RetrofitProvider(
    private val moshi: Moshi,
    private val apiUrl: String
) {
    fun provide(): Retrofit {
        val client = OkHttpClient.Builder()
            .handleErrors()
            .setLogger(HttpLoggingInterceptor.Level.BODY)
            .setApiKey()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .client(client)
            .setConverters()
            .baseUrl(apiUrl)
            .build()
    }

    private fun Retrofit.Builder.setConverters() =
        addConverterFactory(MoshiConverterFactory.create(moshi))

    private fun OkHttpClient.Builder.setLogger(
        logLevel: HttpLoggingInterceptor.Level
    ): OkHttpClient.Builder {
        if (BuildConfig.DEBUG) {
            addInterceptor(HttpLoggingInterceptor().apply {
                level = logLevel
            })
        }
        return this
    }

    private fun OkHttpClient.Builder.handleErrors() = addInterceptor { chain ->
        val response = chain.proceed(chain.request())

        if (response.isSuccessful) {
            response
        } else {
            val code = response.code
            val body = response.body

            throw if (body == null) {
                ApiException(ApiExceptionBody(code, "Server response is empty"))
            } else {
                try {
                    val error = moshi.adapter(ErrorJson::class.java).fromJson(body.string())?.status
                    ApiException(error)
                } catch (ex: Exception) {
                    ApiException(ApiExceptionBody(code, "Cannot parse error: ${ex.message}"))
                } finally {
                    body.close()
                }
            }
        }
    }

    private fun OkHttpClient.Builder.setApiKey() = addInterceptor { chain ->
        var req = chain.request()
        val url = req.url.newBuilder().addQueryParameter("api_key", BuildConfig.API_KEY).build()
        req = req.newBuilder().url(url).build()
        chain.proceed(req)
    }
}