package io.github.mohamedisoliman.pixapay.domain.search

import io.github.mohamedisoliman.pixapay.UiState
import io.github.mohamedisoliman.pixapay.data.entities.ImageModel

sealed class SearchState(
    val isLoading: Boolean = false,
    val result: List<ImageModel>? = null,
    val searchText: String? = null,
    val throwable: Throwable? = null,
) : UiState {

    class IDLE(searchText: String? = "") : SearchState(searchText = searchText)

    class EmptyResult(searchText: String?) : SearchState(searchText = searchText)

    class Loading(searchText: String?) : SearchState(searchText = searchText, isLoading = true)

    class Success(
        result: List<ImageModel>?,
        searchText: String?,
    ) : SearchState(result = result, searchText = searchText)

    class Error(
        searchText: String? = null,
        throwable: Throwable? = null,
    ) : SearchState(searchText = searchText, throwable = throwable)

}