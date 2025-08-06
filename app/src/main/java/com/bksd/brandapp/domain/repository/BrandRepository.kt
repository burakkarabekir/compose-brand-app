package com.bksd.brandapp.domain.repository

import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import com.bksd.brandapp.domain.model.Brand
import com.bksd.brandapp.domain.model.BrandDetail

/**
 * Repository interface for Brand operations
 */
interface BrandRepository {

    suspend fun searchBrands(query: String): DataResult<List<Brand>, DataError.Remote>
    suspend fun getBrandDetail(brand: String): DataResult<BrandDetail, DataError>
}