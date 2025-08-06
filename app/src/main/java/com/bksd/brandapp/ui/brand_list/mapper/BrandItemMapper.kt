package com.bksd.brandapp.ui.brand_list.mapper

import com.bksd.brandapp.component.brand_item.BrandItemUi
import com.bksd.brandapp.domain.model.Brand

fun Brand.toBrandItemUi(): BrandItemUi = BrandItemUi(
    icon = this.icon.orEmpty(), name = this.name.orEmpty(), domain = this.domain.orEmpty(), brandId = this.brandId.orEmpty()
)

fun List<Brand>.toBrandItemUiList(): List<BrandItemUi> = this.map { it.toBrandItemUi() }