package com.bksd.brandapp.component.shimmer

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.cardShimmer(isLoading: Boolean) = shimmer(
    cornerRadius = 8.dp,
    durationMillis = 1200,
    enabled = isLoading
)

@Composable
fun Modifier.textShimmer(isLoading: Boolean) = shimmer(
    cornerRadius = 4.dp,
    durationMillis = 1000,
    enabled = isLoading
)

@Composable
fun Modifier.imageShimmer(isLoading: Boolean) = shimmer(
    cornerRadius = 12.dp,
    direction = ShimmerDirection.TopLeftToBottomRight,
    enabled = isLoading
)