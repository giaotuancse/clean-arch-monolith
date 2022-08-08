package com.tuangiao.monolith.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tuangiao.monolith.BuildConfig
import com.tuangiao.monolith.network.AuthInterceptor
import com.tuangiao.monolith.network.LegoServices
import com.tuangiao.monolith.network.LegoServices.Companion.ENDPOINT
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const  val timeout: Long = 30
    }

    /**
     * Provides [LegoServices] instance
     */
    @Provides
    @Singleton
    fun provideLegoService(retrofit: Retrofit): LegoServices {
        return retrofit.create(LegoServices::class.java)
    }

    /**
     * Provides [HttpLoggingInterceptor] instance
     */
    @Provides
    fun provideLoggingInterceptor() : HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG)
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        else
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE

        return httpLoggingInterceptor
    }

    /**
     * Provides [OkHttpClient] instance
     */
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor) : OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .writeTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(AuthInterceptor("1e93220b06914b6ada6bc2254077565f"))
            .retryOnConnectionFailure(true)
            .build()
    }

    /**
     * Provides [Retrofit] instance
     */
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        val contentType = "application/json".toMediaType()
        return Retrofit.Builder()
            .baseUrl(ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }
}