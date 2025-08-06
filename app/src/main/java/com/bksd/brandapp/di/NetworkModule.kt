package com.bksd.brandapp.di

import com.bksd.brandapp.data.repository.BrandRepositoryImpl
import com.bksd.brandapp.domain.repository.BrandRepository
import com.bksd.brandapp.domain.usecase.FetchBrandDetailUseCase
import com.bksd.brandapp.domain.usecase.SearchBrandsUseCase
import com.bksd.brandapp.network.NetworkConfig
import com.bksd.brandapp.network.datasource.RemoteBrandDataSource
import com.bksd.brandapp.network.datasource.RemoteBrandDataSourceImpl
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin dependency injection module for network layer
 */
val networkModule = module {

    // HTTP Client - Singleton
    single { NetworkConfig.createHttpClient() }

    // API Services - Singleton
    singleOf(::RemoteBrandDataSourceImpl) bind RemoteBrandDataSource::class

    // Repositories - Singleton
    singleOf(::BrandRepositoryImpl) { bind<BrandRepository>() }
}

/**
 * Koin dependency injection module for use cases
 */
val useCaseModule = module {
    // Brand-specific Use Cases - Factory
    factory { SearchBrandsUseCase(get()) }
    factory { FetchBrandDetailUseCase(get()) }
}
