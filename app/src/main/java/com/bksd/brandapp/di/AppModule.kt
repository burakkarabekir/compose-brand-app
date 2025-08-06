package com.bksd.brandapp.di

import com.bksd.brandapp.ui.brand_detail.BrandDetailViewModel
import com.bksd.brandapp.ui.brand_list.BrandListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModules = module {
    viewModelOf(::BrandListViewModel)
    viewModelOf(::BrandDetailViewModel)
}