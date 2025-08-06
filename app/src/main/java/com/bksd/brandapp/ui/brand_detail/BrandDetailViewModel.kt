package com.bksd.brandapp.ui.brand_detail

import androidx.lifecycle.viewModelScope
import com.bksd.brandapp.core_domain.onFailure
import com.bksd.brandapp.core_domain.onSuccess
import com.bksd.brandapp.core_ui.BaseViewModel
import com.bksd.brandapp.domain.usecase.FetchBrandDetailUseCase
import com.bksd.brandapp.extension.simpleLaunch
import com.bksd.brandapp.ui.brand_detail.mapper.toBrandDetailUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

class BrandDetailViewModel(
    private val brandDetailUseCase: FetchBrandDetailUseCase,
    private val domain: String
) :
    BaseViewModel<BrandDetailScreenUiState, BrandDetailScreenEvent, BrandDetailScreenEffect>(
        BrandDetailScreenUiState()
    ) {

    private val _state = MutableStateFlow(BrandDetailScreenUiState())
    override val state = _state.onStart { getDetail(domain) }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            _state.value
        )

    private val _effect = Channel<BrandDetailScreenEffect>(Channel.BUFFERED)
    override val effect = _effect.receiveAsFlow()

    override fun onEvent(event: BrandDetailScreenEvent) {
        when (event) {
            BrandDetailScreenEvent.BackClicked -> simpleLaunch {
                _effect.send(BrandDetailScreenEffect.NavigateBack)
            }
            is BrandDetailScreenEvent.DescriptionClicked -> Unit
        }
    }

    fun getDetail(domain: String) = simpleLaunch {
        brandDetailUseCase(domain)
            .collect { brandList ->
                brandList.onSuccess { detail ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            uiModel = BrandDetailScreenUi(
                                detail = detail.toBrandDetailUi(),
                            )
                        )
                    }
                }.onFailure { error ->
                    _state.update { it.copy(isLoading = false) }
                    simpleLaunch {
                        _effect.send(BrandDetailScreenEffect.ShowError("Search failed: $error"))
                    }
                }
            }
    }
}