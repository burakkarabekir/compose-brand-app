package com.bksd.brandapp.data.dto.brand


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BrandDto(
    @SerialName("claimed")
    val claimed: Boolean?,
    @SerialName("colors")
    val colors: List<ColorDto>?,
    @SerialName("company")
    val company: CompanyDto?,
    @SerialName("description")
    val description: String?,
    @SerialName("domain")
    val domain: String?,
    @SerialName("fonts")
    val fonts: List<FontDto>?,
    @SerialName("id")
    val id: String?,
    @SerialName("images")
    val images: List<ImageDto>?,
    @SerialName("isNsfw")
    val isNsfw: Boolean?,
    @SerialName("links")
    val links: List<LinkDto>?,
    @SerialName("logos")
    val logos: List<LogoDto>?,
    @SerialName("longDescription")
    val longDescription: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("qualityScore")
    val qualityScore: Double?,
    @SerialName("urn")
    val urn: String?
)

@Serializable
data class LogoDto(
    @SerialName("formats")
    val formats: List<FormatDto>? = null,
    @SerialName("tags")
    val tags: List<String?>? = null,
    @SerialName("theme")
    val theme: String? = null,
    @SerialName("type")
    val type: String? = null
)

@Serializable
data class FontDto(
    @SerialName("name")
    val name: String?,
    @SerialName("origin")
    val origin: String?,
    @SerialName("originId")
    val originId: String?,
    @SerialName("type")
    val type: String?,
    @SerialName("weights")
    val weights: List<String?>?
)

@Serializable
data class LinkDto(
    @SerialName("name")
    val name: String?,
    @SerialName("url")
    val url: String?
)

@Serializable
data class ImageDto(
    @SerialName("formats")
    val formats: List<FormatDto?>?,
    @SerialName("tags")
    val tags: List<String?>?,
    @SerialName("type")
    val type: String?
)

@Serializable
data class FormatDto(
    @SerialName("background")
    val background: String?,
    @SerialName("format")
    val format: String?,
    @SerialName("height")
    val height: Int?,
    @SerialName("size")
    val size: Int?,
    @SerialName("src")
    val src: String?,
    @SerialName("width")
    val width: Int?
)

@Serializable
data class ColorDto(
    @SerialName("brightness")
    val brightness: Int?,
    @SerialName("hex")
    val hex: String?,
    @SerialName("type")
    val type: String?
)

@Serializable
data class CompanyDto(
    @SerialName("employees")
    val employees: Int?,
    @SerialName("financialIdentifiers")
    val financialIdentifiers: FinancialIdentifiersDto?,
    @SerialName("foundedYear")
    val foundedYear: Int?,
    @SerialName("industries")
    val industries: List<IndustryDto?>?,
    @SerialName("kind")
    val kind: String?,
    @SerialName("location")
    val location: LocationDto?
)

@Serializable
data class FinancialIdentifiersDto(
    @SerialName("isin")
    val isin: List<String?>?,
    @SerialName("ticker")
    val ticker: List<String?>?
)

@Serializable
data class LocationDto(
    @SerialName("city")
    val city: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("countryCode")
    val countryCode: String?,
    @SerialName("region")
    val region: String?,
    @SerialName("state")
    val state: String?,
    @SerialName("subregion")
    val subregion: String?
)


@Serializable
data class IndustryDto(
    @SerialName("emoji")
    val emoji: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("parent")
    val parent: ParentDto?,
    @SerialName("score")
    val score: Double?,
    @SerialName("slug")
    val slug: String?
)

@Serializable
data class ParentDto(
    @SerialName("emoji")
    val emoji: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("slug")
    val slug: String?
)