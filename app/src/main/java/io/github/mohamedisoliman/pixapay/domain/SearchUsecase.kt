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
            .map { if (it.isEmpty()) SearchState.Empty else SearchState.Success(it) }
            .onStart { emit(SearchState.Loading) }
            .catch { emit(SearchState.Error(it)) }
    }

}

sealed class SearchState : UiState {
    object Empty : SearchState()
    class Success(val images: List<ImageModel>) : SearchState()
    class Error(val throwable: Throwable) : SearchState()
    object Loading : SearchState()
}