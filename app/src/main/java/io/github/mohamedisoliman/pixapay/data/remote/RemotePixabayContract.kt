package io.github.mohamedisoliman.pixapay.data.remote

import io.github.mohamedisoliman.pixapay.BuildConfig
import io.github.mohamedisoliman.pixapay.data.entities.PixabaySearchResponse
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RemotePixabayContract {

    @GET(".")
    suspend fun search(
        @Query("q") query: String,
        @Query("image_type") imageType: String = ImageTypes.PHOTO.key,
    ): PixabaySearchResponse

}


fun pixabayApi(
    baseUrl: HttpUrl = BuildConfig.BASE_URL.toHttpUrl(),
    client: () -> OkHttpClient = { makeOkHttpClient() },
): RemotePixabayContract {
    val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(client())
        .build()

    return retrofit.create(RemotePixabayContract::class.java)
}

fun makeOkHttpClient(
    logging: () -> Interceptor = { loggingInterceptor() },
    authorization: () -> Interceptor = { authorizationInterceptor() },
): OkHttpClient =
    OkHttpClient.Builder()
        .addInterceptor(logging())
        .addInterceptor(authorization())
        .build()

private fun authorizationInterceptor() = Interceptor {
    val url: HttpUrl = it.request().url
        .newBuilder()
        .addQueryParameter("key", BuildConfig.API_KEY)
        .build()
    val request: Request = it.request().newBuilder().url(url).build()
    it.proceed(request)
}

private fun loggingInterceptor(): Interceptor =
    HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }


enum class ImageTypes(val key: String) {
    PHOTO("photo")
}


