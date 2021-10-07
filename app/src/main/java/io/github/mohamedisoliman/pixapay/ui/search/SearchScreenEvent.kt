package io.github.mohamedisoliman.pixapay.ui.search

import io.github.mohamedisoliman.pixapay.UiEvent


sealed class SearchScreenEvent : UiEvent

data class SearchClicked(val query: String) : SearchScreenEvent()

data class SearchQueryChanged(val query: String) : SearchScreenEvent()

data class SearchQueryUpdated(val query: String) : SearchScreenEvent()



