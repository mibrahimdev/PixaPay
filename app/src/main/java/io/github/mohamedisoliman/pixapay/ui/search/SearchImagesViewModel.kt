package io.github.mohamedisoliman.pixapay.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mohamedisoliman.pixapay.domain.SearchState
import io.github.mohamedisoliman.pixapay.domain.SearchState.*
import io.github.mohamedisoliman.pixapay.domain.SearchUsecase
import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    val searchUsecase: SearchUsecase,
) : ViewModel() {

    var searchViewState = MutableStateFlow<SearchState>(Empty)
        private set

    var searchQueryState = MutableStateFlow("fruits")
        private set


    init {
        onSearchClicked()
    }

    fun search(query: String) {
        searchUsecase(query = query)
            .onEach { searchViewState.value = it }
            .launchIn(viewModelScope)
    }


    fun onSearchChange(searchText: String) {
        searchQueryState.value = searchText
    }


    fun findImage(id: Long): ImageUiModel? =
        (searchViewState.value as? Success)?.images?.find { it.imageId == id }


    fun onSearchClicked() {
        search(searchQueryState.value)
    }

}
