package com.bksd.brandapp.domain.model

import com.bksd.brandapp.ui.brand_detail.model.BrandLinkUi
import java.text.NumberFormat
import java.util.Locale

data class BrandDetail(
    val claimed: Boolean?,
    val colors: List<Color>?,
    val company: Company?,
    val description: String?,
    val domain: String?,
    val fonts: List<Font>?,
    val id: String?,
    val images: List<Image>?,
    val isNsfw: Boolean?,
    val links: List<Link>?,
    val logos: List<Logo>?,
    val longDescription: String?,
    val name: String?,
    val qualityScore: Double?,
    val urn: String?
) {
    fun mapBrandLinks() = BrandLinkUi(
        youtubeLink = links?.find { it.name == BrandLinkType.YOUTUBE.name }?.url,
        webLink = links?.find { it.name == BrandLinkType.WEB.name }?.url,
        linkedinLink = links?.find { it.name == BrandLinkType.LINKEDIN.name }?.url,
        facebookLink = links?.find { it.name == BrandLinkType.FACEBOOK.name }?.url,
        instagramLink = links?.find { it.name == BrandLinkType.INSTAGRAM.name }?.url,
        twitterLink = links?.find { it.name == BrandLinkType.TWITTER.name }?.url,
    )
}

data class Company(
    val employees: Int?,
    val financialIdentifiers: FinancialIdentifiers?,
    val foundedYear: Int?,
    val industries: List<Industry?>?,
    val kind: String?,
    val location: Location?
) {
    fun formatEmployeeCount(): String? = when {
        employees == null -> null
        employees >= 10000 -> {
            val formatter = NumberFormat.getNumberInstance(Locale.US)
            "${formatter.format(employees)}+"
        }

        employees >= 1000 -> {
            val formatter = NumberFormat.getNumberInstance(Locale.US)
            formatter.format(employees)
        }

        else -> employees.toString()
    }
}

data class FinancialIdentifiers(
    val isin: List<String?>?,
    val ticker: List<String?>?
)

data class Color(
    val brightness: Int?,
    val hex: String?,
    val type: String?
)

data class Image(
    val formats: List<Format?>?,
    val tags: List<String?>?,
    val type: String?
)

data class Font(
    val name: String?,
    val origin: String?,
    val originId: String?,
    val type: String?,
    val weights: List<String?>?
)

data class Link(
    val name: String?,
    val url: String?
)

data class Industry(
    val emoji: String?,
    val id: String?,
    val name: String?,
    val parent: Parent?,
    val score: Double?,
    val slug: String?
)

data class Logo(
    val formats: List<Format>? = null,
    val tags: List<String?>? = null,
    val theme: String? = null,
    val type: String? = null
)

data class Parent(
    val emoji: String?,
    val id: String?,
    val name: String?,
    val slug: String?
)

data class Format(
    val background: String?,
    val format: String?,
    val height: Int?,
    val size: Int?,
    val src: String?,
    val width: Int?
)

data class Location(
    val city: String?,
    val country: String?,
    val countryCode: String?,
    val region: String?,
    val state: String?,
    val subregion: String?
) {
    fun getLocationText(): String? =
        if (!city.isNullOrEmpty() && !countryCode.isNullOrEmpty()) "$city, $countryCode" else null
}

private enum class BrandLinkType(val type: String) {
    YOUTUBE("youtube"),
    TWITTER("twitter"),
    LINKEDIN("linkedin"),
    WEB("crunchbase"),
    FACEBOOK("facebook"),
    INSTAGRAM("instagram")
}