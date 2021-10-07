package io.github.mohamedisoliman.pixapay.domain

import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.entities.PixabayImage
import io.github.mohamedisoliman.pixapay.domain.search.SearchState
import io.github.mohamedisoliman.pixapay.domain.search.SearchUsecase
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.net.UnknownHostException

class SearchUsecaseTest {


    @Test
    fun `search() THEN start with Loading state`() = runBlocking {
        val hit = mockk<PixabayImage>()
        val repository = mockRepository(flowOf(listOf(hit, hit, hit)))

        val result = SearchUsecase(repository).invoke("flowers").first()

        assert(result is SearchState.Loading)
    }

    @Test
    fun `search() with Exception THEN return Error State`() = runBlocking {
        val repository = mockRepository(flow {
            throw UnknownHostException()
        })

        val result = SearchUsecase(repository).invoke("flowers").last()

        assert((result is SearchState.Error) && result.throwable is UnknownHostException)
    }


    @Test
    fun `search() with empty Results THEN return Empty state`() = runBlocking {
        val repository = mockRepository(flowOf(emptyList()))

        val result = SearchUsecase(repository).invoke("flowers").last()

        assert((result is SearchState.EmptyResult))
    }

    @Test
    fun `search() with None empty Results THEN return Success state`() = runBlocking {
        val repository = mockRepository(flow {
            emit(listOf(PixabayImage(), PixabayImage(), PixabayImage()))
        })


        val result = SearchUsecase(repository).invoke("flowers").last()

        assert((result is SearchState.Success) && result.result?.size == 3)
    }


    @Test
    fun `search() with None empty Results THEN Loading then Success`() = runBlocking {
        val repository = mockRepository(flow {
            emit(listOf(PixabayImage(), PixabayImage(), PixabayImage()))
        })


        val result = SearchUsecase(repository).invoke("flowers").toList()
        assert(result.first() is SearchState.Loading)
        assert(result.drop(1).first() is SearchState.Success)
        assert(result.count() == 2)
    }


    private fun mockRepository(flowReturn: Flow<List<PixabayImage>>) =
        object : ImagesRepositoryContract {
            override fun search(query: String): Flow<List<PixabayImage>> = flowReturn
        }


}