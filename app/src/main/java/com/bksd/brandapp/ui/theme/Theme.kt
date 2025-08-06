package com.bksd.brandapp.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Brand App Color Palette
private val BrandPrimary = Color(0xFF1976D2)           // Modern Blue
private val BrandPrimaryVariant = Color(0xFF1565C0)    // Darker Blue
private val BrandSecondary = Color(0xFF00BCD4)         // Cyan
private val BrandSecondaryVariant = Color(0xFF0097A7)  // Darker Cyan
private val BrandAccent = Color(0xFFFF9800)            // Orange
private val BrandError = Color(0xFFE53935)             // Red
private val BrandSuccess = Color(0xFF4CAF50)           // Green

// Light Theme Colors
private val LightSurface = Color(0xFFFAFAFA)           // Light Gray
private val LightBackground = Color(0xFFFFFFFF)        // Pure White
private val LightOnSurface = Color(0xFF1C1C1E)         // Dark Gray
private val LightOnBackground = Color(0xFF1C1C1E)      // Dark Gray
private val LightOutline = Color(0xFFE0E0E0)           // Light Border

// Dark Theme Colors
private val DarkSurface = Color(0xFF1E1E1E)            // Dark Gray
private val DarkBackground = Color(0xFF121212)         // Almost Black
private val DarkOnSurface = Color(0xFFE1E1E1)          // Light Gray
private val DarkOnBackground = Color(0xFFE1E1E1)       // Light Gray
private val DarkOutline = Color(0xFF2C2C2E)            // Dark Border

private val DarkColorScheme = darkColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = BrandPrimaryVariant,
    onPrimaryContainer = Color.White,

    secondary = BrandSecondary,
    onSecondary = Color.White,
    secondaryContainer = BrandSecondaryVariant,
    onSecondaryContainer = Color.White,

    tertiary = BrandAccent,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFF8F00),
    onTertiaryContainer = Color.White,

    error = BrandError,
    onError = Color.White,
    errorContainer = Color(0xFFD32F2F),
    onErrorContainer = Color.White,

    background = DarkBackground,
    onBackground = DarkOnBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface,
    surfaceVariant = Color(0xFF2C2C2E),
    onSurfaceVariant = Color(0xFFB0B0B0),

    outline = DarkOutline,
    outlineVariant = Color(0xFF404040),

    inverseSurface = LightSurface,
    inverseOnSurface = LightOnSurface,
    inversePrimary = BrandPrimaryVariant,
)

private val LightColorScheme = lightColorScheme(
    primary = BrandPrimary,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF0D47A1),

    secondary = BrandSecondary,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0F7FA),
    onSecondaryContainer = Color(0xFF006064),

    tertiary = BrandAccent,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFE0B2),
    onTertiaryContainer = Color(0xFFE65100),

    error = BrandError,
    onError = Color.White,
    errorContainer = Color(0xFFFFEBEE),
    onErrorContainer = Color(0xFFB71C1C),

    background = LightBackground,
    onBackground = LightOnBackground,
    surface = LightSurface,
    onSurface = LightOnSurface,
    surfaceVariant = Color(0xFFF5F5F5),
    onSurfaceVariant = Color(0xFF424242),

    outline = LightOutline,
    outlineVariant = Color(0xFFBDBDBD),

    inverseSurface = DarkSurface,
    inverseOnSurface = DarkOnSurface,
    inversePrimary = Color(0xFF90CAF9),
)

@Composable
fun BrandAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}