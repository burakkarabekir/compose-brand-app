package com.bksd.brandapp.ui.brand_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.bksd.brandapp.R
import com.bksd.brandapp.ui.theme.BrandAppTheme

@Composable
fun BrandDetailHeader(
    modifier: Modifier = Modifier, imageUrl: String?, logoUrl: String?,
    onBackClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(180.dp)
    ) {
        // Banner image
       imageUrl?.let {url ->
           AsyncImage(
               model = ImageRequest.Builder(context)
                   .data(url)
                   .placeholder(R.drawable.ic_placeholder)
                   .crossfade(true)
                   .build(),
               contentDescription = "Banner Image",
               modifier = Modifier
                   .fillMaxWidth()
                   .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
               contentScale = ContentScale.Crop
           )
       }

        // Back Navigation Icon (positioned over the banner)
        IconButton(
            modifier = Modifier
                .size(40.dp)
                .offset(x = 16.dp, y = 16.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.7f))
                .align(Alignment.TopStart),
            onClick = { onBackClicked() }
        ) {
            Icon(
                Icons.Default.ArrowBackIosNew,
                contentDescription = "Back",
                tint = Color.White
            )
        }
        logoUrl?.let { url ->
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(url)
                    .placeholder(R.drawable.ic_placeholder)
                    .crossfade(true)
                    .build(),
                contentDescription = "Brand Logo",
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = 16.dp, y = 50.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(2.dp, Color.Black, CircleShape)
                    .align(Alignment.BottomStart),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview
@Composable
fun ComposeHeaderPreview() {
    BrandAppTheme {
        BrandDetailHeader(
            imageUrl = "www.apple.com",
            logoUrl = "www.apple.com",
        )
    }
}