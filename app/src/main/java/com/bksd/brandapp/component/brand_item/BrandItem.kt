package com.bksd.brandapp.component.brand_item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bksd.brandapp.R
import com.bksd.brandapp.ui.theme.BrandAppTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import com.bksd.brandapp.component.shimmer.imageShimmer
import com.bksd.brandapp.component.shimmer.shimmer
import com.bksd.brandapp.component.shimmer.textShimmer

@Composable
fun BrandItem(
    modifier: Modifier = Modifier,
    model: BrandItemUi,
    onClickItem: (BrandItemUi) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClickItem(model) }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(model.icon) // Replace with your actual image URL or data source
                .placeholder(R.drawable.ic_placeholder)
                .crossfade(true)
                .build(),
            contentDescription = "Brand Icon",
            modifier = Modifier
                .clip(CircleShape)
                .size(40.dp)
                .weight(1f)
                .imageShimmer(model.icon.isEmpty()),
            contentScale = ContentScale.Fit
        )
        Text(
            text = model.name,
            style = TextStyle.Default.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .weight(5f)
                .padding(horizontal = 8.dp)
                .textShimmer(isLoading = model.name.isEmpty())
        )

        Text(
            text = model.domain,
            style = TextStyle.Default.copy(fontSize = 14.sp, textAlign = TextAlign.End),
            modifier = Modifier
                .weight(4f)
                .padding(end = 4.dp)
                .textShimmer(isLoading = model.domain.isEmpty())
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BrandItemPreview() {
    val model = BrandItemUi(
        icon = "https://example.com/icon.png", name = "Google", domain = "google.com", brandId = "1"

    )
    BrandAppTheme {
        BrandItem(model = model)
    }
}