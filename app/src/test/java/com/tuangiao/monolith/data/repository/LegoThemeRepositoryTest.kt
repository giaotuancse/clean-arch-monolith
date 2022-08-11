package com.tuangiao.monolith.data.repository

import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.common.exception.ExceptionMapper
import com.tuangiao.monolith.common.mapper.LegoThemeDataDomainMapper
import com.tuangiao.monolith.data.datasource.LegoThemeDataSource
import com.tuangiao.monolith.util.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import kotlin.time.ExperimentalTime

@ExperimentalTime
@ExperimentalCoroutinesApi
@SmallTest
internal class LegoThemeRepositoryTest {
    @MockK
    private lateinit var legoThemeDataSource: LegoThemeDataSource
    private var mapper = LegoThemeDataDomainMapper()
    private var errorMapper = ExceptionMapper()

    lateinit var subject : LegoThemeRepository

   @Before
   fun setUp() {
       MockKAnnotations.init(this)
       subject = LegoThemeRepository(legoThemeDataSource, mapper, errorMapper)

   }

    @Test
    fun `getLegoThemes() should return in flow once datasource has returned the valid data`() = runBlockingTest{
        // give mock data return value
        val mockResult = TestDataGenerator.generateLegoThemesModel()
        coEvery { legoThemeDataSource.getLegoTheme() } returns mockResult

        val flow = subject.getLegoThemes()
        flow.test{
            val result = expectItem()
            Truth.assertThat(result).isInstanceOf(DataResult.Success::class.java)
            val data = (result as DataResult.Success).data
            Truth.assertThat(data).containsExactlyElementsIn(mapper.fromList(mockResult))
            expectComplete()
        }

        coVerify { legoThemeDataSource.getLegoTheme() }
    }

    @Test
    fun `getLegoThemes() should return ResultError once exception happens`() = runBlockingTest {
        coEvery { legoThemeDataSource.getLegoTheme() } throws Exception()
        var flow = subject.getLegoThemes()
        flow.test {
            val result = expectItem();
            Truth.assertThat(result).isInstanceOf(DataResult.Error::class.java)
            expectComplete()
        }

        coVerify { legoThemeDataSource.getLegoTheme() }

    }
}