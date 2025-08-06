package com.bksd.brandapp.ui.brand_detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import com.bksd.brandapp.R
import com.bksd.brandapp.component.UiText
import com.bksd.brandapp.ui.theme.BrandAppTheme

@Composable
fun FontsSection(modifier: Modifier = Modifier,fonts: Map<String, String>) {
    Column(modifier = modifier) {
        Text(text = UiText.StringResource(R.string.title_fonts).asString(),
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(fonts.entries.toList()) { (fontName, fontType) ->
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .border(1.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(8.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.onBackground),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = getInitials(fontName),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Column(horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.Top) {
                        Text(text = fontName, color = MaterialTheme.colorScheme.onBackground)
                        Text(
                            modifier = Modifier.padding(top = 4.dp),
                            text = fontType,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }
        }
    }
}

private fun getInitials(text: String): String = text.split(" ")
    .mapNotNull { it.firstOrNull()?.uppercaseChar() }
    .joinToString("")
    .take(2) // Limit to 2 characters for better display

@PreviewLightDark
@Composable
fun FontSelectionPreview() {
    BrandAppTheme {
        FontsSection(fonts = mapOf(Pair("Google Sans", "title")))
    }
}