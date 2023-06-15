package com.tuangiao.monolith.data.repository


import androidx.test.filters.MediumTest
import app.cash.turbine.test
import com.google.common.truth.Truth
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.common.exception.ExceptionMapper
import com.tuangiao.monolith.common.mapper.LegoThemeDataDomainMapper
import com.tuangiao.monolith.data.datasource.LegoThemeDataSource
import com.tuangiao.monolith.utils.TestDataGenerator
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
@MediumTest
class LegoThemeRepositoryTest {
    private lateinit var subject: LegoThemeRepository

    @MockK
    private lateinit var datasource: LegoThemeDataSource
    private var mapper = LegoThemeDataDomainMapper()
    private var errorMapper = ExceptionMapper();


    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        subject = LegoThemeRepository(datasource, mapper, errorMapper)
    }

    @Test
    fun `getLegoThemes should return exception when error is return from datasource`() = runBlockingTest {
        coEvery { datasource.getLegoTheme() } throws Exception()
        val flow = subject.getLegoThemes()
        flow.test{
            val result = expectItem()
            Truth.assertThat(result).isInstanceOf(DataResult.Error::class.java)
            expectComplete()
        }
        coVerify { datasource.getLegoTheme() }
    }

    @Test
    fun `when datasource has valid data, getLegoThemes should return data`() = runBlockingTest {
        val mockResult = TestDataGenerator.generateLegoThemesDataModel();
        coEvery { datasource.getLegoTheme() } returns mockResult
        val flow = subject.getLegoThemes()
        flow.test {
            val result = expectItem()
            Truth.assertThat(result).isInstanceOf(DataResult.Error::class.java)
            val data = (result as DataResult.Success).data
            Truth.assertThat(data).containsExactlyElementsIn(mapper.fromList(mockResult))
        }
    }
}