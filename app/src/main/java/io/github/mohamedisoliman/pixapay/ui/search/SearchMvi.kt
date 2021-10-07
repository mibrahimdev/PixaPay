package io.github.mohamedisoliman.pixapay.ui.search

import io.github.mohamedisoliman.pixapay.UiEvent
import io.github.mohamedisoliman.pixapay.UiState


sealed class SearchScreenEvent : UiEvent {
    data class SearchClicked(val query: String) : SearchScreenEvent()
}
