package com.bksd.brandapp.data.repository

import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import com.bksd.brandapp.core_domain.map
import com.bksd.brandapp.data.mapper.toBrandDetail
import com.bksd.brandapp.data.mapper.toSearchList
import com.bksd.brandapp.domain.model.Brand
import com.bksd.brandapp.domain.model.BrandDetail
import com.bksd.brandapp.domain.repository.BrandRepository
import com.bksd.brandapp.network.datasource.RemoteBrandDataSource

/**
 * Implementation of BrandRepository with caching capabilities
 */
class BrandRepositoryImpl(
    private val remoteDataSource: RemoteBrandDataSource
) : BrandRepository {
    override suspend fun searchBrands(query: String): DataResult<List<Brand>, DataError.Remote> {
        return remoteDataSource
            .searchBrands(query)
            .map { dto ->
                dto.toSearchList()
            }
    }

    override suspend fun getBrandDetail(brand: String): DataResult<BrandDetail, DataError> {
        return remoteDataSource
            .fetchBrandDetail(brand)
            .map { dto ->
                dto.toBrandDetail()
            }
    }
}
