package io.github.mohamedisoliman.pixapay.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mohamedisoliman.pixapay.data.entities.ImageModel
import io.github.mohamedisoliman.pixapay.domain.search.SearchState
import io.github.mohamedisoliman.pixapay.domain.search.SearchUsecase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val searchUsecase: SearchUsecase,
    private val _states: MutableStateFlow<SearchState>,
) : ViewModel() {

    val states = _states.asStateFlow()
    private val _queryState = MutableStateFlow("fruits")
    val query by lazy { _queryState.asStateFlow() }
    private val _searchClick = MutableSharedFlow<String>()

    lateinit var navigateToDetails: (Long) -> Unit

    init {
        subscribeToEvents()
        onSearchClicked()
    }


    private fun subscribeToEvents() {
        merge(
            searchCurrentQuery(),
            searchWhileTyping(),
            updateUiSearchText()
        )
            .flatMapMerge { it.toUsecase() }
            .onEach { _states.value = it }
            .launchIn(viewModelScope)

    }


    fun onSearchClicked() {
        viewModelScope.launch { _searchClick.emit(_queryState.value) }
    }


    fun onSearchQueryChange(query: String) {
        viewModelScope.launch { _queryState.emit(query) }
    }

    fun findImage(id: Long): ImageModel? = _states.value.result?.find { it.imageId == id }


    private fun SearchScreenEvent.toUsecase(): Flow<SearchState> = when (this) {
        is SearchClicked -> searchUsecase(this.query)
        is SearchQueryChanged -> searchUsecase(this.query)
        is SearchQueryUpdated -> flowOf(SearchState.IDLE(searchText = this.query))
    }

    private fun updateUiSearchText() =
        _queryState.asSharedFlow()
            .map { SearchQueryUpdated(it) }

    private fun searchWhileTyping() =
        _queryState.asSharedFlow()
            .map { SearchQueryChanged(it) }
            .debounce(700)

    private fun searchCurrentQuery() =
        _searchClick.asSharedFlow()
            .map { SearchClicked(it) }
            .onStart { SearchClicked("fruits") }

}
