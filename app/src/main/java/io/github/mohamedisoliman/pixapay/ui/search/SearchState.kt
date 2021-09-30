package io.github.mohamedisoliman.pixapay.ui.search

import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel

sealed class SearchState {
    object Empty : SearchState()
    class Result(val images: List<ImageUiModel>) : SearchState()
    class Error(val throwable: Throwable) : SearchState()
    object Loading : SearchState()
}