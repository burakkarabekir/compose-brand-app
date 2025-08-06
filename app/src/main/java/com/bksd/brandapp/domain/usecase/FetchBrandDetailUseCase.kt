package com.bksd.brandapp.domain.usecase

import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import com.bksd.brandapp.domain.model.BrandDetail
import com.bksd.brandapp.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use case for getting brand details using Brand API
 */
class FetchBrandDetailUseCase(
    private val brandRepository: BrandRepository
) {

    operator fun invoke(
        brandDomain: String
    ): Flow<DataResult<BrandDetail, DataError>> = flow {
        // Business logic validation
        require(brandDomain.isNotBlank()) { "Brand domain cannot be blank" }
        require(brandDomain.length >= 2) { "Brand domain must be at least 2 characters" }

        // Clean and validate the brand name
        val cleanBrandName = brandDomain.trim().lowercase()

        // Call repository and emit result
        val result = brandRepository.getBrandDetail(cleanBrandName)
        emit(result)
    }
}
