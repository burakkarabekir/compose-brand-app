package com.bksd.brandapp.ui.brand_list

import com.bksd.brandapp.component.brand_item.BrandItemUi

data class BrandListScreenUiState(
    val isLoading: Boolean = false,
    val uiModel: BrandListScreenUi = BrandListScreenUi()
)

data class BrandListScreenUi(
    val brands: List<BrandItemUi> = emptyList(),
    val query: String = ""
)

sealed class BrandListScreenEvent {
    data class SearchQueryChanged(val query: String) : BrandListScreenEvent()
    data object SearchClicked : BrandListScreenEvent()
    data object ClearSearch : BrandListScreenEvent()
    data class BrandItemClicked(val domain: String) : BrandListScreenEvent()
}

sealed class BrandListScreenEffect {
    data class ShowError(val message: String) : BrandListScreenEffect()
    data class NavigateToDetail(val domain: String) : BrandListScreenEffect()
}

fun BrandListScreenUiState.updateQuery(
    uiModel: BrandListScreenUi,
    searchText: String
): BrandListScreenUiState = copy(uiModel = uiModel.copy(query = searchText))