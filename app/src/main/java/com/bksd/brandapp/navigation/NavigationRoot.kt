package com.bksd.brandapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import com.bksd.brandapp.ui.brand_detail.BrandDetailScreen
import com.bksd.brandapp.ui.brand_list.BrandListScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun NavigationRoot(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(BrandListScreenRoute)
    NavDisplay(
        modifier = modifier,
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
            rememberSceneSetupNavEntryDecorator()
        ),
        entryProvider = entryProvider {
            entry<BrandListScreenRoute>(metadata = TwoPaneScene.twoPane()) {
                BrandListScreen(modifier = Modifier) { brandName ->
                    backStack.add(BrandDetailScreenRoute(brandName))
                }
            }
            entry<BrandDetailScreenRoute>(metadata = TwoPaneScene.twoPane()) {
                BrandDetailScreen(
                    modifier = Modifier,
                    viewModel = koinViewModel {
                        parametersOf(it.domain)
                    },
                    onNavigationIconClicked = {backStack.removeLastOrNull()})
            }
        }
    )
}

