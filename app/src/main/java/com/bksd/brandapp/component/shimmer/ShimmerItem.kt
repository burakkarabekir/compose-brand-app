package com.bksd.brandapp.component.shimmer

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.shimmer(
    cornerRadius: Dp = 0.dp,
    enabled: Boolean = true,
    durationMillis: Int = 1200,
    colors: List<Color> = defaultShimmerColors(),
    gradientWidth: Float = 1.2f,
    direction: ShimmerDirection = ShimmerDirection.LeftToRight
): Modifier {
    if (!enabled) return this
    val transition = rememberInfiniteTransition(label = "Shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = -200f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationMillis,
                easing = FastOutSlowInEasing
            )
        ),
        label = "Animation"
    )

    return this.drawWithCache {
        val cornerPx = cornerRadius.toPx()
        val brush = createShimmerBrush(
            colors = colors,
            translateAnim = translateAnim,
            direction = direction,
            gradientWidth = gradientWidth,
            size = size
        )
        onDrawWithContent {
            drawRoundRect(
                brush = brush,
                cornerRadius = CornerRadius(cornerPx, cornerPx),
                size = size,
                alpha = 0.5f
            )
        }
    }
}

private fun createShimmerBrush(
    colors: List<Color>,
    translateAnim: Float,
    direction: ShimmerDirection,
    gradientWidth: Float,
    size: Size
): Brush {
    val (start, end) = when (direction) {
        ShimmerDirection.LeftToRight ->
            Offset(translateAnim, 0f) to Offset(
                translateAnim + size.width / gradientWidth,
                size.height
            )

        ShimmerDirection.RightToLeft ->
            Offset(
                size.width - translateAnim,
                0f
            ) to Offset(size.width - translateAnim - size.width / gradientWidth, size.height)

        ShimmerDirection.TopToBottom ->
            Offset(0f, translateAnim) to Offset(
                size.width,
                translateAnim + size.height / gradientWidth
            )

        ShimmerDirection.BottomToTop ->
            Offset(0f, size.height - translateAnim) to Offset(
                size.width,
                size.height - translateAnim - size.height / gradientWidth
            )

        ShimmerDirection.TopLeftToBottomRight ->
            Offset(
                translateAnim,
                translateAnim
            ) to Offset(
                translateAnim + size.width / gradientWidth,
                translateAnim + size.height / gradientWidth
            )

        ShimmerDirection.TopRightToBottomLeft ->
            Offset(
                size.width - translateAnim,
                translateAnim
            ) to Offset(
                size.width - translateAnim - size.width / gradientWidth,
                translateAnim + size.height / gradientWidth
            )
    }

    return Brush.linearGradient(
        colors = colors,
        start = start,
        end = end
    )
}

@Composable
private fun defaultShimmerColors(): List<Color> {
    val theme = MaterialTheme.colorScheme
    return remember(theme) {
        listOf(
            theme.surfaceVariant.copy(alpha = 0.3f),
            theme.surface.copy(alpha = 0.8f),
            theme.surfaceVariant.copy(alpha = 0.3f)
        )
    }
}