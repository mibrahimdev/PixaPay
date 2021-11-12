package io.github.mohamedisoliman.pixapay.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import io.github.mohamedisoliman.pixapay.domain.search.SearchState
import io.github.mohamedisoliman.pixapay.ui.search.SearchScreenEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow


@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelSearchModule {

    @Provides
    @ViewModelScoped
    fun providesEventsFlow(): MutableSharedFlow<SearchScreenEvent> = MutableSharedFlow()


    @Provides
    @ViewModelScoped
    fun providesStatesFlow(): MutableStateFlow<SearchState> = MutableStateFlow(SearchState.IDLE())
}