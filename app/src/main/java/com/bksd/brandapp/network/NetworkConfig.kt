package com.bksd.brandapp.network

import com.bksd.brandapp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import timber.log.Timber

/**
 * Network configuration class that provides a configured Ktor HTTP client
 * with proper error handling, logging, and content negotiation
 */
object NetworkConfig {

    private const val BRAND_BASE_URL = BuildConfig.BASE_URL
    private const val API_KEY: String = BuildConfig.API_KEY
    private const val CLIENT_ID: String = BuildConfig.CLIENT_ID
    private const val CONNECT_TIMEOUT: Long = 15_000L
    private const val SOCKET_TIMEOUT: Long = 15_000L
    private const val REQUEST_TIMEOUT: Long = 30_000L
    private const val RETRY_COUNT: Int = 3

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = false
        prettyPrint = true
        coerceInputValues = true
    }

    /**
     * Creates and configures the HTTP client with all necessary plugins
     */
    fun createHttpClient(): HttpClient {
        return HttpClient(Android) {
            // Base URL configuration
            defaultRequest {
                url(BRAND_BASE_URL)
                headers.append("Content-Type", "application/json")
            }

            // JSON content negotiation
            install(ContentNegotiation) {
                json(json)
            }

            // Request/Response logging (only in debug builds)
            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            Timber.d("HTTP Client: $message")
                        }
                    }
                    level = LogLevel.ALL
                }
            }

            // Timeout configuration
            install(HttpTimeout) {
                requestTimeoutMillis = REQUEST_TIMEOUT
                connectTimeoutMillis = CONNECT_TIMEOUT
                socketTimeoutMillis = SOCKET_TIMEOUT
            }

            // Retry configuration
            install(HttpRequestRetry) {
                retryOnServerErrors(maxRetries = RETRY_COUNT)
                retryIf(maxRetries = RETRY_COUNT) { request, response ->
                    response.status.value.let { it == 429 || it in 500..599 }
                }
                exponentialDelay()
            }

            // Default headers
            install(DefaultRequest) {
                headers.append("User-Agent", "BrandApp-Android/${BuildConfig.VERSION_NAME}")
                headers.append("Accept", "application/json")
            }
        }
    }
}
