package io.github.mohamedisoliman.pixapay.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mohamedisoliman.pixapay.data.ImagesRepository
import io.github.mohamedisoliman.pixapay.data.remote.pixabayApi
import io.github.mohamedisoliman.pixapay.domain.SearchUsecase
import io.github.mohamedisoliman.pixapay.ui.uiModels.ImageUiModel
import io.github.mohamedisoliman.pixapay.ui.search.SearchViewState.*
import kotlinx.coroutines.flow.*

@HiltViewModel
class SearchImagesViewModel(
    val searchUsecase: SearchUsecase,
) : ViewModel() {

    var searchViewState = MutableStateFlow<SearchViewState>(Empty)
        private set

    var searchQueryState = MutableStateFlow("fruits")
        private set


    init {
        onSearchClicked()
    }

    fun search(query: String) {
        searchUsecase(query = query)
            .onStart { searchViewState.value = Loading }
            .catch { searchViewState.value = Error(it) }
            .onEach { searchViewState.value = Result(it) }
            .launchIn(viewModelScope)
    }


    fun onSearchChange(searchText: String) {
        searchQueryState.value = searchText
    }


    fun findImage(id: Long): ImageUiModel? =
        (searchViewState.value as? Result)?.images?.find { it.imageId == id }


    fun onSearchClicked() {
        search(searchQueryState.value)
    }

}
