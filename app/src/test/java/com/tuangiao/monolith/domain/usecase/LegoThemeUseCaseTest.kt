package com.tuangiao.monolith.domain.usecase

import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.data.repository.LegoThemeRepository
import com.tuangiao.monolith.domain.mapper.LegoThemeDomainUIMapper
import com.tuangiao.monolith.util.MainCoroutineRule
import com.tuangiao.monolith.util.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@MediumTest
internal class LegoThemeUseCaseTest {

    private lateinit var subject : LegoThemeUseCase

    @MockK
    private lateinit var legoThemeRepository: LegoThemeRepository

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private var mapper =  LegoThemeDomainUIMapper()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = LegoThemeUseCase(legoThemeRepository, mainCoroutineRule.dispatcher, mapper)
    }


    @Test
    fun `getLegoThemes() should return in flow once datasource has returned the valid data`() = runBlockingTest{
        // give mock data return value
        val mockResult = TestDataGenerator.generateLegoThemesDomainModel()
        val mockResultFlow = flowOf(DataResult.Success(mockResult))
        coEvery { legoThemeRepository.getLegoThemes() } returns mockResultFlow


        val flow = subject.getLegoThemes()
        flow.test{
            val result = expectItem()
            Truth.assertThat(result).isInstanceOf(DataResult.Success::class.java)
            val data = (result as DataResult.Success).data
            Truth.assertThat(data).containsExactlyElementsIn(mockResult)
            expectComplete()
        }

        coVerify { legoThemeRepository.getLegoThemes() }
    }


}