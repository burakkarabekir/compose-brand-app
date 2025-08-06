package com.bksd.brandapp.ui.brand_detail.mapper

import com.bksd.brandapp.domain.model.BrandDetail
import com.bksd.brandapp.domain.model.Company
import com.bksd.brandapp.domain.model.Link
import com.bksd.brandapp.ui.brand_detail.model.BrandDetailUi
import com.bksd.brandapp.ui.brand_detail.model.BrandLinkUi
import com.bksd.brandapp.ui.brand_detail.model.CompanyUi
import java.text.NumberFormat
import java.util.Locale

fun BrandDetail.toBrandDetailUi() = BrandDetailUi(
    name = this.name.orEmpty(),
    icon = extractMainLogo(),
    banner = extractBannerImage(),
    verified = this.claimed == true,
    description = this.description.orEmpty(),
    longDescription = this.longDescription,
    links = mapBrandLinks(this.links),
    company = mapCompany(this.company),
    logos = extractDarkThemeLogos(),
    colors = extractColors(),
    fonts = extractFonts(),
)

private fun BrandDetail.extractMainLogo(): String = this.logos
    ?.find { it.type == LogoType.ICON.type }
    ?.formats
    ?.firstOrNull()?.src.orEmpty()

private fun BrandDetail.extractBannerImage(): String = this.images
    ?.find { it.type == ImageType.BANNER.type }
    ?.formats
    ?.find { it?.format == ImageFormatType.JPEG.type }
    ?.src.orEmpty()

private fun BrandDetail.extractDarkThemeLogos(): List<String> = this.logos
    ?.flatMap { logo ->
        logo.formats
            ?.filter { format ->
                format.format in LogoFormatType.getValidFormats()
            }
            ?.mapNotNull { format -> format.src?.takeIf { it.isNotBlank() } }
            .orEmpty()
    }
    .orEmpty()

private fun BrandDetail.extractColors(): List<String> = this.colors
    ?.mapNotNull { it.hex?.takeIf { hex -> hex.isNotBlank() } }
    .orEmpty()

private fun BrandDetail.extractFonts(): Map<String, String>? = this.fonts
    ?.filter { it.name?.isNotBlank() == true && it.type?.isNotBlank() == true }
    ?.takeIf { it.isNotEmpty() }
    ?.associate { it.name.orEmpty() to mapFontType(it.type.orEmpty()) }

private fun mapCompany(company: Company?): CompanyUi = CompanyUi(
    employees = company?.employees?.let { formatEmployeeCount(it) },
    foundedYear = company?.foundedYear?.toString(),
    kind = company?.kind?.let { kind ->
        CompanyKindType.entries
            .find { it.name.equals(kind, ignoreCase = true) }?.type
    },
    location = company?.location?.let { "${it.city.orEmpty()}, ${it.countryCode.orEmpty()}" }
        ?.takeIf { it != ", " }
)

private fun mapFontType(type: String) = FontType.entries
        .find { it.type == type }
        ?.displayText ?: type

private fun mapBrandLinks(links: List<Link>?): BrandLinkUi =
    if (links.isNullOrEmpty()) BrandLinkUi()
    else BrandLinkUi(
        youtubeLink = links.findLinkByType(BrandLinkType.YOUTUBE),
        webLink = links.findLinkByType(BrandLinkType.WEB),
        linkedinLink = links.findLinkByType(BrandLinkType.LINKEDIN),
        facebookLink = links.findLinkByType(BrandLinkType.FACEBOOK),
        instagramLink = links.findLinkByType(BrandLinkType.INSTAGRAM),
        twitterLink = links.findLinkByType(BrandLinkType.TWITTER),
    )

private fun List<Link>.findLinkByType(type: BrandLinkType): String? =
    this.find { it.name.equals(type.type, ignoreCase = true) }
        ?.url
        ?.takeIf { it.isNotBlank() }

private fun formatEmployeeCount(employees: Int): String = when {
    employees >= 10000 -> {
        "${NumberFormat.getNumberInstance(Locale.US).format(employees)}+"
    }

    employees >= 1000 -> {
        NumberFormat.getNumberInstance(Locale.US).format(employees)
    }

    else -> employees.toString()
}

// Enums organized by domain
private enum class LogoThemeType(val type: String) {
    DARK("dark"),
    LIGHT("light")
}

private enum class LogoType(val type: String) {
    SYMBOL("symbol"),
    ICON("icon"),
    LOGO("logo")
}

private enum class LogoFormatType(val type: String) {
    JPEG("jpeg"),
    PNG("png"),
    WEBP("webp"),
    SVG("svg");

    companion object {
        fun getValidFormats(): Set<String> = setOf(PNG.type, JPEG.type)
    }
}

private enum class ImageFormatType(val type: String) {
    JPEG("jpeg"),
    WEBP("webp")
}

private enum class ImageType(val type: String) {
    BANNER("banner")
}

private enum class CompanyKindType(val type: String) {
    NON_PROFIT("Non Profit"),
    PUBLIC_COMPANY("Public Company"),
    PRIVATELY_HELD("Privately Held")
}

private enum class BrandLinkType(val type: String) {
    YOUTUBE("youtube"),
    TWITTER("twitter"),
    LINKEDIN("linkedin"),
    WEB("crunchbase"),
    FACEBOOK("facebook"),
    INSTAGRAM("instagram")
}

private enum class FontType(val type: String, val displayText: String) {
    TITLE(
        "title",
        "HEADING FONT"
    ),
    BODY("body", "BODY FONT")
}
