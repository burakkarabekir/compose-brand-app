package com.bksd.brandapp.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object BrandListScreenRoute : NavKey

@Serializable
data class BrandDetailScreenRoute(
    val domain: String
) : NavKey