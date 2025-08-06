package com.bksd.brandapp.network.datasource

import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import com.bksd.brandapp.data.dto.brand.BrandDto
import com.bksd.brandapp.data.dto.search.SearchDto

interface RemoteBrandDataSource {
    suspend fun fetchBrandDetail(brand: String): DataResult<BrandDto, DataError.Remote>
    suspend fun searchBrands(brand: String): DataResult<SearchDto, DataError.Remote>
}