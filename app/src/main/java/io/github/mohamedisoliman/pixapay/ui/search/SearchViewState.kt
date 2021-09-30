package io.github.mohamedisoliman.pixapay.ui.search

sealed class SearchViewState {
    object Empty : SearchViewState()
    class Result(val images: List<ImageUiModel>) : SearchViewState()
    class Error(val throwable: Throwable) : SearchViewState()
    object Loading : SearchViewState()
}