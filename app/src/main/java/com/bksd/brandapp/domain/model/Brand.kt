package com.bksd.brandapp.domain.model

data class Brand(
    val name: String? = null,
    val brandId: String? = null,
    val claimed: Boolean? = null,
    val domain: String? = null,
    val icon: String? = null,
    val qualityScore: Double? = null,
    val score: Double? = null,
    val verified: Boolean? = null
)


