package com.bksd.brandapp.ui.brand_list

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bksd.brandapp.component.SearchBar
import com.bksd.brandapp.component.brand_item.BrandItem
import com.bksd.brandapp.component.brand_item.BrandItemUi
import com.bksd.brandapp.core_ui.component.AppLoading
import com.bksd.brandapp.extension.CollectFlowAsEvent
import com.bksd.brandapp.ui.theme.BrandAppTheme
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@Composable
fun BrandListScreen(
    modifier: Modifier = Modifier,
    viewModel: BrandListViewModel = koinViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onBrandSelected: (String) -> Unit
) {

    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    CollectFlowAsEvent(viewModel.effect) { effect ->
        when (effect) {
            is BrandListScreenEffect.ShowError -> Toast.makeText(context, effect.message, LENGTH_SHORT ).show()
            is BrandListScreenEffect.NavigateToDetail -> {
                onBrandSelected(effect.domain)
            }
        }
    }
    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        if (uiState.isLoading) {
            AppLoading()
        }

        BrandListContent(
            modifier = modifier,
            uiModel = uiState.uiModel,
            onEvent = viewModel::onEvent
        )
    }
}

@Composable
fun BrandListContent(
    modifier: Modifier = Modifier,
    uiModel: BrandListScreenUi,
    onEvent: (BrandListScreenEvent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top,
    ) {
        SearchBar(
            query = uiModel.query,
            onQueryChange = {query ->
                onEvent(BrandListScreenEvent.SearchQueryChanged(query))
            },
            onSearch = { onEvent(BrandListScreenEvent.SearchClicked) },
            onClear = { onEvent(BrandListScreenEvent.ClearSearch) })
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(
                items = uiModel.brands,
                key = { it.brandId }
            ) { item ->
                BrandItem(
                    modifier = Modifier,
                    model = BrandItemUi(
                        icon = item.icon, name = item.name, domain = item.domain, brandId = item.brandId
                    )
                ) {
                    onEvent(BrandListScreenEvent.BrandItemClicked(item.domain))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BrandListScreenPreview() {
    BrandAppTheme {
        BrandListContent(
            uiModel = BrandListScreenUi(
                brands = listOf(
                    BrandItemUi(icon = "", name = "apple", domain = "apple.com", brandId = "1"),
                    BrandItemUi(icon = "", name = "facebook", domain = "facebook.com", brandId = "2"),
                    BrandItemUi(icon = "", name = "google", domain = "google.com", brandId = "3")
                )
            ),
        )
    }
}