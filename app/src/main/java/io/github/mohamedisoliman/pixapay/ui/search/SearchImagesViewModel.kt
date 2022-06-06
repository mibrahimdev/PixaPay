package io.github.mohamedisoliman.pixapay.ui.search

import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.mohamedisoliman.pixapay.BaseViewModel
import io.github.mohamedisoliman.pixapay.data.entities.ImageModel
import io.github.mohamedisoliman.pixapay.domain.SearchState
import io.github.mohamedisoliman.pixapay.domain.SearchState.EmptyResult
import io.github.mohamedisoliman.pixapay.domain.SearchState.Loading
import io.github.mohamedisoliman.pixapay.domain.SearchUsecase
import io.github.mohamedisoliman.pixapay.ui.search.SearchScreenEvent.SearchClicked
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val searchUsecase: SearchUsecase,
) : BaseViewModel<SearchScreenEvent, SearchState>(EmptyResult) {

    private val _queryState = MutableStateFlow("fruits")
    val query = _queryState.asStateFlow()

    lateinit var navigateToDetails: (Long) -> Unit

    init {
        emitEvent(SearchClicked(query.value))
    }


    fun onSearchQueryChange(query: String) {
        _queryState.value = query
    }

    fun findImage(id: Long): ImageModel? = states.value.result?.find { it.imageId == id }


    override fun SearchScreenEvent.eventToUsecase(): Flow<SearchState> {
        return when (this) {
            is SearchClicked -> searchUsecase(this.query)
        }
    }
}
