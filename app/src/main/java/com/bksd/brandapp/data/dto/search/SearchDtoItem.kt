package com.bksd.brandapp.data.dto.search

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchDtoItem(
    @SerialName("brandId")
    val brandId: String? = null,
    @SerialName("claimed")
    val claimed: Boolean? = null,
    @SerialName("domain")
    val domain: String? = null,
    @SerialName("icon")
    val icon: String? = null,
    @SerialName("name")
    val name: String? = null,
    @SerialName("qualityScore")
    val qualityScore: Double? = null,
    @SerialName("_score")
    val score: Double? = null,
    @SerialName("verified")
    val verified: Boolean? = null
)