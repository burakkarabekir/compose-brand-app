package com.bksd.brandapp.ui.brand_detail.component

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bksd.brandapp.R
import com.bksd.brandapp.core_ui.openBrowser
import com.bksd.brandapp.ui.theme.BrandAppTheme

@Composable
fun SocialMediaIcon(@DrawableRes iconResId: Int, link: String) {
    val context = LocalContext.current
    IconButton(onClick = { context.openBrowser(link) }) {
        Icon(
            painter = painterResource(iconResId),
            contentDescription = "Social Media Icon",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(28.dp)
        )
    }
}

@Preview
@Composable
fun SocialMediaIconPreview(){
    BrandAppTheme {
        SocialMediaIcon(R.drawable.ic_youtube, "www.google.com")
    }
}