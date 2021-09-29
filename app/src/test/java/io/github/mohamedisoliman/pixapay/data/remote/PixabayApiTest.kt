package io.github.mohamedisoliman.pixapay.data.remote

import io.github.mohamedisoliman.pixapay.Util
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import retrofit2.HttpException


@ExperimentalCoroutinesApi
class PixabayApiTest {


    @get:Rule
    val mockWebServer = MockWebServer()


    private val api by lazy {
        pixabayApi(mockWebServer.url("/"), client = {
            OkHttpClient.Builder().build()
        })
    }

    @Test
    fun `search() Then return images results`() = runBlocking {

        val response = MockResponse()
            .setBody(Util.jsonAsString("PixaSearchResponse.json"))
            .setResponseCode(200)

        mockWebServer.enqueue(response = response)


        val search = api.search("flowers")

        Assert.assertTrue(search.totalHits == 500)

    }


    @Test(expected = HttpException::class)
    fun `search() With Client Error Then failure`() = runBlocking {

        val response = MockResponse()
            .setBody(Util.jsonAsString("PixaSearchResponse.json"))
            .setResponseCode(400)

        mockWebServer.enqueue(response = response)

        val search = api.search("flowers")

    }
}