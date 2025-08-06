package com.bksd.brandapp.ui.brand_detail

import com.bksd.brandapp.ui.brand_detail.model.BrandDetailUi

data class BrandDetailScreenUiState(
    val isLoading: Boolean = true,
    val uiModel: BrandDetailScreenUi? = null
)

data class BrandDetailScreenUi(
    val detail: BrandDetailUi? = null,
)

sealed class BrandDetailScreenEvent {
    data class DescriptionClicked(val query: String) : BrandDetailScreenEvent()
    data object BackClicked : BrandDetailScreenEvent()
}

sealed class BrandDetailScreenEffect {
    data class ShowError(val message: String) : BrandDetailScreenEffect()
    data object NavigateBack : BrandDetailScreenEffect()
}