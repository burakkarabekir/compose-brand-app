package com.bksd.brandapp.ui.brand_detail

import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bksd.brandapp.core_ui.component.SetSystemBarsLightAppearance
import com.bksd.brandapp.extension.CollectFlowAsEvent
import com.bksd.brandapp.ui.brand_detail.component.BrandDetailBody
import com.bksd.brandapp.ui.brand_detail.component.BrandDetailHeader
import com.bksd.brandapp.ui.brand_detail.model.BrandDetailUi
import com.bksd.brandapp.ui.theme.BrandAppTheme
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: BrandDetailViewModel = koinViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onNavigationIconClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    CollectFlowAsEvent(viewModel.effect) { effect ->
        when (effect) {
            is BrandDetailScreenEffect.ShowError -> Toast.makeText(
                context,
                effect.message,
                LENGTH_SHORT
            ).show()

            BrandDetailScreenEffect.NavigateBack -> onNavigationIconClicked()
        }
    }

    SetSystemBarsLightAppearance(
        isAppearanceLightStatusBars = false,
        isAppearanceLightNavigationBars = false
    )

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.surface
    ) {
        uiState.uiModel?.detail?.let { uiModel ->
            BrandDetailContent(
                modifier = modifier,
                uiModel = uiModel,
                onEvent = viewModel::onEvent
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrandDetailContent(
    modifier: Modifier = Modifier,
    uiModel: BrandDetailUi,
    onEvent: (BrandDetailScreenEvent) -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top
    ) {
        BrandDetailHeader(
            modifier = Modifier,
            imageUrl = uiModel.banner,
            logoUrl = uiModel.icon,
            onBackClicked = { onEvent(BrandDetailScreenEvent.BackClicked) })
        Spacer(modifier = Modifier.height(60.dp))
        BrandDetailBody(modifier = Modifier, uiModel)
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@PreviewLightDark
@Composable
private fun BrandDetailScreenPreview() {
    BrandAppTheme {
        BrandDetailContent(
            uiModel = BrandDetailUi(
                name = "Apple",
                icon = "www.apple.com",
                banner = "image",
                description = "Apple Inc. is an American multinational",
                longDescription = "Apple Inc. It was founded on April 1, 1976, by Steve Jobs, Steve Wozniak, and Ronald Wayne. The company's headquarters are located in Cupertino, California. Apple is widely known for its flagship products, including the iPhone"
            )
        )
    }
}