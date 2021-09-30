package io.github.mohamedisoliman.pixapay.domain

import io.github.mohamedisoliman.pixapay.data.ImagesRepositoryContract
import io.github.mohamedisoliman.pixapay.data.entities.toImageUiModel
import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel
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
            .map { list -> list.map { it.toImageUiModel() } }
            .map {
                if (it.isEmpty()) SearchState.Empty else SearchState.Success(it)
            }
            .catch { emit(SearchState.Error(it)) }
            .onStart { emit(SearchState.Loading) }
    }

}

sealed class SearchState {
    object Empty : SearchState()
    class Success(val images: List<ImageUiModel>) : SearchState()
    class Error(val throwable: Throwable) : SearchState()
    object Loading : SearchState()
}