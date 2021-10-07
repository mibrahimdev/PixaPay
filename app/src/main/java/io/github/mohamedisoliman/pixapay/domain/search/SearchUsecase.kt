package io.github.mohamedisoliman.pixapay.domain.search

import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.entities.toImageModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class SearchUsecase @Inject constructor(
    private val imagesRepository: ImagesRepositoryContract,
) {

    operator fun invoke(query: String): Flow<SearchState> {
        return imagesRepository.search(query)
            .map { list -> list.map { it.toImageModel() } }
            .map {
                if (it.isEmpty())
                    SearchState.EmptyResult(query)
                else
                    SearchState.Success(searchText = query, result = it)
            }.onStart { emit(SearchState.Loading(query)) }
            .catch { emit(SearchState.Error(searchText = query, it)) }
    }

}

