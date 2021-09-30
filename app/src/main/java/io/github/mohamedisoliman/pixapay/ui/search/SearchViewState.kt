package io.github.mohamedisoliman.pixapay.ui.search

import io.github.mohamedisoliman.pixapay.ui.models.ImageUiModel

sealed class SearchViewState {
    object Empty : SearchViewState()
    class Result(val images: List<ImageUiModel>) : SearchViewState()
    class Error(val throwable: Throwable) : SearchViewState()
    object Loading : SearchViewState()
}