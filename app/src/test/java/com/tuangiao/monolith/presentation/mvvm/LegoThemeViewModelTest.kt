package com.tuangiao.monolith.presentation.mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.domain.mapper.LegoThemeDomainUIMapper
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import com.tuangiao.monolith.domain.usecase.LegoThemeUseCase
import com.tuangiao.monolith.presentation.model.LegoThemeUIModel
import com.tuangiao.monolith.util.MainCoroutineRule
import com.tuangiao.monolith.util.TestCoroutineRule
import com.tuangiao.monolith.util.TestDataGenerator
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import observeForTesting
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Exception
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@MediumTest
internal class LegoThemeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var subject: LegoThemeViewModel

    @MockK
    private lateinit var legoThemeUseCase: LegoThemeUseCase

    private val mapper = LegoThemeDomainUIMapper()

    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    @MockK
    private lateinit var dataOrbserver: Observer<DataResult<List<LegoThemeUIModel>>>


    /**
     * this testing class especially customized for test flow.AsLiveData()
     */


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher) //  These setting to testing coroutine viewModelScope.launch {} in code

    }

    @After
    fun tearDown() {
        // These setting to testing coroutine viewModelScope.launch {} in code
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `expect getThemes should be called when viewmodel inited`() = testCoroutineRule.runTest {
        val mockResult = TestDataGenerator.generateLegoThemesDomainModel()
        val mockResultFlow = flowOf(DataResult.Success(mockResult))
        coEvery { legoThemeUseCase.getLegoThemes() } returns mockResultFlow
        subject  = LegoThemeViewModel(legoThemeUseCase, mapper)


        subject.legoThemes.observeForever(dataOrbserver)

        coVerify { legoThemeUseCase.getLegoThemes() }
        verify { dataOrbserver.onChanged(any()) }
        subject.legoThemes.observeForTesting {
            advanceTimeBy(101) // could also use 'advanceTimeBy(100); runCurrent()' or even 'advanceTimeByAndRun(100)' extension
            // Then
            assertThat(it.value).isInstanceOf(DataResult.Success::class.java)
            assertThat((it.value as DataResult.Success).data).isEqualTo(mapper.fromList(TestDataGenerator.generateLegoThemesDomainModel()))
        }
    }

    @Test
    fun `expect result is Result Error when getThemes failed`() = testCoroutineRule.runTest {
        val data = DataResult.Error<List<LegoThemeDomainModel>>(Exception())
        val mockResultFlow = flowOf(data)
        coEvery { legoThemeUseCase.getLegoThemes() }  returns mockResultFlow
        subject  = LegoThemeViewModel(legoThemeUseCase, mapper)

        coVerify { legoThemeUseCase.getLegoThemes() }
//        verify { dataOrbserver.onChanged(any()) }
        subject.legoThemes.observeForTesting {
            advanceTimeBy(101) // could also use 'advanceTimeBy(100); runCurrent()' or even 'advanceTimeByAndRun(100)' extension
            // Then
            assertThat(it.value).isInstanceOf(DataResult.Error::class.java)
//            assertThat((it.value as DataResult.Success).data).isEqualTo(mapper.fromList(TestDataGenerator.generateLegoThemesDomainModel()))
        }

    }

}