package com.bksd.brandapp.ui.brand_detail.component

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.bksd.brandapp.ui.theme.BrandAppTheme
import com.bksd.brandapp.component.shimmer.shimmer
import com.bksd.brandapp.component.shimmer.textShimmer

@Composable
fun BrandDescriptionText(
    modifier: Modifier = Modifier,
    description: String,
    longDescription: String?
) {
    var isExpanded by remember { mutableStateOf(false) }
    val hasLongDescription = !longDescription.isNullOrEmpty()

    Text(
        modifier = modifier
            .clickable(enabled = hasLongDescription) {
                isExpanded = !isExpanded
            }
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        text = if (isExpanded && hasLongDescription) longDescription else description,
        style = MaterialTheme.typography.bodyMedium,
        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
        overflow = TextOverflow.Ellipsis,
        color = MaterialTheme.colorScheme.primary
    )
}

@Preview(showBackground = true)
@Composable
private fun BrandDescriptionTextPreview() {
    BrandAppTheme {
        BrandDescriptionText(
            description = "Apple Inc. is an American multinational",
            longDescription = "Apple Inc. It was founded on April 1, 1976, by Steve Jobs, Steve Wozniak, and Ronald Wayne. The company's headquarters are located in Cupertino, California. Apple is widely known for its flagship products, including the iPhone"
        )
    }
}