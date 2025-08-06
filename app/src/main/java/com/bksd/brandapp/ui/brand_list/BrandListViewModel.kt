package com.bksd.brandapp.ui.brand_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bksd.brandapp.core_domain.onFailure
import com.bksd.brandapp.core_domain.onSuccess
import com.bksd.brandapp.core_ui.BaseViewModel
import com.bksd.brandapp.domain.usecase.SearchBrandsUseCase
import com.bksd.brandapp.extension.simpleLaunch
import com.bksd.brandapp.ui.brand_list.mapper.toBrandItemUiList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BrandListViewModel(
    private val searchBrandsUseCase: SearchBrandsUseCase
) : BaseViewModel<BrandListScreenUiState, BrandListScreenEvent, BrandListScreenEffect>(BrandListScreenUiState()) {

    private val _state = MutableStateFlow(BrandListScreenUiState())
    override val state: StateFlow<BrandListScreenUiState> = _state.asStateFlow()

    private val _effect = Channel<BrandListScreenEffect>(Channel.BUFFERED)
    override val effect = _effect.receiveAsFlow()

    private var searchJob: Job? = null

    fun getBrands(query: String) = simpleLaunch {
        searchBrandsUseCase(query)
            .collect { brandList ->
                brandList.onSuccess { searchResults ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            uiModel = BrandListScreenUi(
                                brands = searchResults.toBrandItemUiList(),
                                query = _state.value.uiModel.query
                            )
                        )
                    }
                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false) }
                    simpleLaunch {
                        _effect.send(BrandListScreenEffect.ShowError("Search failed: ${error.name}"))
                    }
                }
            }
    }

    override fun onEvent(event: BrandListScreenEvent) {
        when (event) {
            is BrandListScreenEvent.SearchQueryChanged -> {
                _state.update { currentState ->
                    currentState.updateQuery(
                        uiModel = currentState.uiModel,
                        searchText = event.query
                    )
                }


                // Cancel previous search job to avoid multiple concurrent searches
                searchJob?.cancel()

                val query = event.query.trim()

                when {
                    query.isEmpty() -> {
                        // Clear results immediately when search is empty
                        _state.update { currentState ->
                            currentState.copy(
                                uiModel = currentState.uiModel.copy(brands = emptyList()),
                                isLoading = false
                            )
                        }
                    }
                    query.length >= 3 -> {
                        // Start debounced search for queries with 3+ characters
                        searchJob = viewModelScope.launch {
                            delay(300)
                            _state.update { it.copy(isLoading = true) }
                            getBrands(query)
                        }
                    }
                    // For queries with 1-2 characters, do nothing (no search, no clear)
                }
            }
            is BrandListScreenEvent.SearchClicked -> {
                val currentQuery = _state.value.uiModel.query
                if (currentQuery.isNotBlank()) {
                    _state.update { it.copy(isLoading = true) }
                    getBrands(currentQuery)
                } else {
                    simpleLaunch {
                        _effect.send(BrandListScreenEffect.ShowError("Please enter a search term"))
                    }
                }
            }
            is BrandListScreenEvent.ClearSearch -> {
                _state.update { currentState ->
                    currentState.copy(
                        uiModel = currentState.uiModel.copy(
                            query = "",
                            brands = emptyList()
                        ),
                        isLoading = false
                    )
                }
            }
            is BrandListScreenEvent.BrandItemClicked -> {
                simpleLaunch {
                    _effect.send(BrandListScreenEffect.NavigateToDetail(event.domain))
                }
            }
        }
    }
}