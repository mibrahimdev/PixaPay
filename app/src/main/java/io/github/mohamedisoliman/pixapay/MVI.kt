package io.github.mohamedisoliman.pixapay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

interface UiEvent

interface UiState


@OptIn(FlowPreview::class, kotlinx.coroutines.ExperimentalCoroutinesApi::class)
abstract class BaseViewModel<E : UiEvent, S : UiState>(initState: S) : ViewModel() {


    private val events = MutableSharedFlow<E>()
    private val _states = MutableStateFlow(initState)
    val states = _states.asStateFlow()

    init {
        events.flatMapMerge { it.eventToUsecase() }
            .onEach { receiveState(it) }
            .launchIn(viewModelScope)
    }


    abstract fun E.eventToUsecase(): Flow<S>


    fun emitEvent(event: E) {
        viewModelScope.launch { events.emit(event) }
    }

    open fun receiveState(state: S) {
        _states.value = state
    }

}

