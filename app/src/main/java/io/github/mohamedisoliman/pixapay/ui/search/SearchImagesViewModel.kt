package io.github.mohamedisoliman.pixapay.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mohamedisoliman.pixapay.domain.SearchState
import io.github.mohamedisoliman.pixapay.domain.SearchState.*
import io.github.mohamedisoliman.pixapay.domain.SearchUsecase
import io.github.mohamedisoliman.pixapay.data.entities.ImageModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    val searchUsecase: SearchUsecase,
) : ViewModel() {

    private var _searchViewState = MutableStateFlow<SearchState>(Empty)
    val searchViewState: StateFlow<SearchState> = _searchViewState

    private var _searchQueryState = MutableStateFlow("fruits")
    val searchQueryState: StateFlow<String> = _searchQueryState


    init {
        onSearchClicked()
    }

    fun search(query: String) {
        searchUsecase(query = query)
            .onEach { _searchViewState.value = it }
            .launchIn(viewModelScope)
    }


    fun onSearchChange(searchText: String) {
        _searchQueryState.value = searchText
    }


    fun findImage(id: Long): ImageModel? =
        (_searchViewState.value as? Success)?.images?.find { it.imageId == id }


    fun onSearchClicked() {
        search(_searchQueryState.value)
    }

}
