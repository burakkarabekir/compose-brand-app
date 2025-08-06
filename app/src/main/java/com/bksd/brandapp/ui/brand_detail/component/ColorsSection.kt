package com.bksd.brandapp.ui.brand_detail.component

import android.R.attr.x
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import androidx.core.graphics.toColorInt
import com.bksd.brandapp.R
import com.bksd.brandapp.component.UiText

@Composable
fun ColorsSection(modifier: Modifier = Modifier, colors: List<String>) {
    Column {
        Text(text = UiText.StringResource(R.string.title_colors).asString(),
            modifier = modifier,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
        )
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            itemsIndexed(colors) { index, colorHex ->
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .offset(x = (index * -(32).dp))
                        .clip(CircleShape)
                        .background(Color(colorHex.toColorInt()))
                        .zIndex((colors.size - index).toFloat())
                )
            }
        }
    }
}