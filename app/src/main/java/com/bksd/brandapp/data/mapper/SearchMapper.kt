package com.bksd.brandapp.data.mapper

import com.bksd.brandapp.data.dto.search.SearchDto
import com.bksd.brandapp.domain.model.Brand

fun SearchDto.toSearchList(): List<Brand> = map {
    Brand(
        name = it.name,
        brandId = it.brandId,
        claimed = it.claimed,
        domain = it.domain,
        icon = it.icon,
        qualityScore = it.qualityScore,
        score = it.score,
        verified = it.verified
    )
}