package com.bksd.brandapp.network.datasource

import com.bksd.brandapp.BuildConfig
import com.bksd.brandapp.core_domain.DataError
import com.bksd.brandapp.core_domain.DataResult
import com.bksd.brandapp.data.dto.brand.BrandDto
import com.bksd.brandapp.data.dto.search.SearchDto
import com.bksd.brandapp.network.extension.safeCall
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter

class RemoteBrandDataSourceImpl(
    private val httpClient: HttpClient
) : RemoteBrandDataSource {

    companion object {
        private const val SEARCH_ENDPOINT = "search"
        private const val DETAIL_ENDPOINT = "brands"
    }

    override suspend fun fetchBrandDetail(brand: String): DataResult<BrandDto, DataError.Remote> {
        return safeCall<BrandDto> {
            httpClient.get(urlString = "${BuildConfig.BASE_URL}/${DETAIL_ENDPOINT}/$brand") {
                header(key = "Authorization", value = "Bearer ${BuildConfig.API_KEY}")
            }
        }
    }

    override suspend fun searchBrands(brand: String): DataResult<SearchDto, DataError.Remote> {
        return safeCall<SearchDto> {
            httpClient.get(urlString = "${BuildConfig.BASE_URL}/${SEARCH_ENDPOINT}/${brand}") {
                parameter(key = "c", value = BuildConfig.CLIENT_ID)
            }
        }
    }
}