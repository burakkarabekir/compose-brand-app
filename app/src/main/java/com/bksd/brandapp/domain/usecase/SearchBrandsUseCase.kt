package com.bksd.brandapp.domain.usecase

import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import com.bksd.brandapp.domain.model.Brand
import com.bksd.brandapp.domain.repository.BrandRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Use case for searching brands using Brand API
 */
class SearchBrandsUseCase(
    private val brandRepository: BrandRepository
) {

    operator fun invoke(
        query: String,
    ): Flow<DataResult<List<Brand>, DataError.Remote>> = flow {
        // Business logic validation
        require(query.isNotBlank()) { "Search query cannot be blank" }
        require(query.length >= 2) { "Search query must be at least 2 characters" }

        // Clean and validate the search query
        val cleanQuery = query.trim()

        // Call repository and emit result
        val result = brandRepository.searchBrands(cleanQuery)
        emit(result)
    }
}
