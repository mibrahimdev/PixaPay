package io.github.mohamedisoliman.pixapay.data

import io.github.mohamedisoliman.pixapay.data.entities.PixabayImage
import io.github.mohamedisoliman.pixapay.data.entities.PixabaySearchResponse
import io.github.mohamedisoliman.pixapay.data.local.LocalPixabayStoreContract
import io.github.mohamedisoliman.pixapay.data.remote.RemotePixabayContract
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ImagesRepositoryTest {


    @Test
    fun `search() WITH hitting Remote then caching results THEN return cache`() = runBlocking {

        //remote
        val remote = mockk<RemotePixabayContract>()
        val response = mockk<PixabaySearchResponse>()
        val expected = listOf(PixabayImage(), PixabayImage(), PixabayImage())

        //local
        val local = mockk<LocalPixabayStoreContract>()


        coEvery { local.getAllImage() } returns expected

        every { response.images } returns expected

        coEvery { remote.search("flowers") } returns response


        val repo = ImagesRepository(remote, local)


        val actual = repo.search("flowers").last()


        assertEquals(expected, actual)

    }
}