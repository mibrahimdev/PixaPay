package io.github.mohamedisoliman.pixapay.ui.search

import androidx.compose.runtime.Composable
import io.github.mohamedisoliman.pixapay.UiEvent
import io.github.mohamedisoliman.pixapay.UiState
import io.github.mohamedisoliman.pixapay.domain.SearchState
import io.github.mohamedisoliman.pixapay.domain.SearchState.*


@Composable
fun UiState.toComposable(
    onItemImageClicked: (Long) -> Unit = {},
    loading: @Composable (Boolean) -> Unit = {},
) = when (this) {
    is Empty -> EmptyView()
    is Error -> ErrorView(this.throwable)
    is Success -> ImageListView(this.images, onImageClicked = onItemImageClicked)
    is Loading -> loading(true)
    else -> NotImplementedError()
}.also {
    loading(this is Loading)
}


sealed class SearchScreenEvent : UiEvent {
    data class SearchQueryChange(val query: String) : SearchScreenEvent()
    object SearchClicked : SearchScreenEvent()
}

data class SearchTextChange(val text: String) : UiState

