package io.github.mohamedisoliman.pixapay.data.remote

import io.github.mohamedisoliman.pixapay.BuildConfig
import io.github.mohamedisoliman.pixapay.data.entities.PixabaySearchDto
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


enum class ImageTypes(val key: String) {
    PHOTO("photo")
}

interface PixabayApi {

    @GET(".")
    suspend fun search(
        @Query("q") query: String,
        @Query("image_type") imageType: String = ImageTypes.PHOTO.key,
    ): PixabaySearchDto

}


fun pixabayApi(): PixabayApi {
    val retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(makeOkHttpClient())
        .build()

    return retrofit.create(PixabayApi::class.java)
}

fun makeOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(loggingInterceptor())
    .addInterceptor(authorizationInterceptor())
    .build()

private fun authorizationInterceptor() = Interceptor {
    val url: HttpUrl = it.request().url()
        .newBuilder()
        .addQueryParameter("key", BuildConfig.API_KEY)
        .build()
    val request: Request = it.request().newBuilder().url(url).build()
    it.proceed(request)
}

private fun loggingInterceptor() =
    HttpLoggingInterceptor().also {
        it.level = HttpLoggingInterceptor.Level.BODY
    }



