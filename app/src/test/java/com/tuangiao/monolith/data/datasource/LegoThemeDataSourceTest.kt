package com.tuangiao.monolith.data.datasource

import androidx.test.filters.MediumTest
import com.google.common.truth.Truth
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.tuangiao.monolith.network.LegoServices
import enqueueResponse
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Retrofit

@MediumTest
internal class LegoThemeDataSourceTest {
    private val mockWebServer = MockWebServer()
    private lateinit var legoServices: LegoServices

    private lateinit var subject: LegoThemeDataSource

    @Before
    fun setUp() {
        val contentType = "application/json".toMediaType()
        legoServices = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
            .create(LegoServices::class.java)
        subject = LegoThemeDataSource(legoServices)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    //todo  add happy case test


    @Test
    fun `once api call being fail with code other than 2xx, exception should be thrown`() = runBlocking {
        mockWebServer.enqueueResponse("", 500)
        val exception = try {
            subject.getLegoTheme()
            null
        } catch (exception: HttpException) {
            exception
        }
        Truth.assertThat(exception?.code()).isEqualTo(500)
    }
}