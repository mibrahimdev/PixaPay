package io.github.mohamedisoliman.pixapay.domain

import io.github.mohamedisoliman.pixapay.UiState
import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.entities.toImageModel
import io.github.mohamedisoliman.pixapay.data.entities.ImageModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class SearchUsecase @Inject constructor(
    private val imagesRepository: ImagesRepositoryContract,
) {

    operator fun invoke(query: String): Flow<SearchState> {
        return imagesRepository.search(query)
            .map { list -> list.map { it.toImageModel() } }
            .map {
                if (it.isEmpty())
                    SearchState.EmptyResult
                else
                    SearchState.Success(searchText = query, result = it)
            }.onStart { emit(SearchState.Loading) }
            .catch { emit(SearchState.Error(searchText = query, it)) }
    }

}

sealed class SearchState(
    val isLoading: Boolean = false,
    val result: List<ImageModel>? = null,
    val searchText: String? = null,
    val throwable: Throwable? = null,
) : UiState {

    class IDLE(searchText: String?) : SearchState(searchText = searchText)

    object EmptyResult : SearchState()

    class Success(
        result: List<ImageModel>?,
        searchText: String?,
    ) : SearchState(result = result, searchText = searchText)

    class Error(
        searchText: String? = null,
        throwable: Throwable? = null,
    ) : SearchState(searchText = searchText, throwable = throwable)

    object Loading : SearchState(isLoading = true)
}