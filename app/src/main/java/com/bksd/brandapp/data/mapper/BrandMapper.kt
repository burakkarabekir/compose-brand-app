package com.bksd.brandapp.data.mapper

import com.bksd.brandapp.data.dto.brand.BrandDto
import com.bksd.brandapp.data.dto.brand.ColorDto
import com.bksd.brandapp.data.dto.brand.CompanyDto
import com.bksd.brandapp.data.dto.brand.FinancialIdentifiersDto
import com.bksd.brandapp.data.dto.brand.FontDto
import com.bksd.brandapp.data.dto.brand.FormatDto
import com.bksd.brandapp.data.dto.brand.ImageDto
import com.bksd.brandapp.data.dto.brand.IndustryDto
import com.bksd.brandapp.data.dto.brand.LinkDto
import com.bksd.brandapp.data.dto.brand.LocationDto
import com.bksd.brandapp.data.dto.brand.LogoDto
import com.bksd.brandapp.data.dto.brand.ParentDto
import com.bksd.brandapp.domain.model.BrandDetail
import com.bksd.brandapp.domain.model.Color
import com.bksd.brandapp.domain.model.Company
import com.bksd.brandapp.domain.model.FinancialIdentifiers
import com.bksd.brandapp.domain.model.Font
import com.bksd.brandapp.domain.model.Format
import com.bksd.brandapp.domain.model.Image
import com.bksd.brandapp.domain.model.Industry
import com.bksd.brandapp.domain.model.Link
import com.bksd.brandapp.domain.model.Location
import com.bksd.brandapp.domain.model.Logo
import com.bksd.brandapp.domain.model.Parent
import okhttp3.internal.format

fun BrandDto.toBrandDetail(): BrandDetail = BrandDetail(
    claimed = claimed,
    colors = colors?.toColor(),
    company = company?.toCompany(),
    description = description,
    domain = domain,
    fonts = fonts?.toFont(),
    id = this.id,
    images = images?.toImage(),
    isNsfw = isNsfw,
    links = links?.toLink(),
    logos = logos?.toLogo(),
    longDescription = longDescription,
    name = name,
    qualityScore = qualityScore,
    urn = urn
)

fun List<ColorDto>.toColor(): List<Color> = map { colorDto ->
    Color(
        brightness = colorDto.brightness,
        hex = colorDto.hex,
        type = colorDto.type
    )
}


fun List<FontDto>.toFont(): List<Font> = map { fontDto ->
    Font(
        name = fontDto.name,
        origin = fontDto.origin,
        originId = fontDto.originId,
        type = fontDto.type,
        weights = fontDto.weights

    )
}

fun List<ImageDto>.toImage(): List<Image> = map { imageDto ->
    Image(
        formats = imageDto.formats?.toFormat(),
        tags = imageDto.tags,
        type = imageDto.type

    )
}

fun List<LinkDto>.toLink(): List<Link> = map { linkDto ->
    Link(
        name = linkDto.name,
        url = linkDto.url
    )
}

fun List<LogoDto>.toLogo(): List<Logo> = map { logoDto ->
    Logo(
        formats = logoDto.formats?.toFormat(),
        tags = logoDto.tags,
        theme = logoDto.theme,
        type = logoDto.type
    )
}

fun FinancialIdentifiersDto.toFinancialIdentifiers(): FinancialIdentifiers = FinancialIdentifiers(
    isin = isin,
    ticker = ticker

)

fun List<IndustryDto?>.toIndustry(): List<Industry?> = map { industryDto ->
    industryDto?.let { Industry(
        emoji = industryDto.emoji,
        id = industryDto.id,
        name = industryDto.name,
        parent = industryDto.parent?.toParent(),
        score = industryDto.score,
        slug = industryDto.slug
    ) }
}

fun LocationDto.toLocation(): Location = Location(
    city = city, country = country, countryCode = countryCode, region = region, state = state, subregion = subregion

)

fun CompanyDto.toCompany() = Company(
    employees = employees,
    financialIdentifiers = financialIdentifiers?.toFinancialIdentifiers(),
    foundedYear = foundedYear,
    industries = industries?.toIndustry(),
    kind = kind,
    location = location?.toLocation()
)

fun List<FormatDto?>.toFormat(): List<Format> = map { formatDto ->
    Format(
        background = formatDto?.background,
        format = formatDto?.format,
        height = formatDto?.height,
        size = formatDto?.size,
        src = formatDto?.src,
        width = formatDto?.width
    )
}

fun ParentDto.toParent(): Parent = Parent(
    emoji = emoji, id = id, name = name, slug = slug

)