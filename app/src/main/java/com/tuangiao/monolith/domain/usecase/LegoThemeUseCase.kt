package com.tuangiao.monolith.domain.usecase

import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.data.repository.LegoThemeRepository
import com.tuangiao.monolith.di.qualifier.IoDispatcher
import com.tuangiao.monolith.domain.mapper.LegoThemeDomainUIMapper
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import com.tuangiao.monolith.presentation.model.LegoThemeUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class LegoThemeUseCase @Inject constructor(
    private val legoThemeRepository: LegoThemeRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher,
    private val mapper : LegoThemeDomainUIMapper
) {
    val f2 = flow {
        emit(listOf(3, 4))
    }

    val f3 = flow {
        emit(listOf(5, 6))
    }


    suspend fun getLegoThemes() = legoThemeRepository.getLegoThemes().flowOn(dispatcher)

    /**
     * sample of combine two flow
     */
//    suspend fun combineLegoThemes() : Flow<DataResult<List<LegoThemeUIModel>>>  {
//        return combine(
//            legoThemeRepository.getLegoThemes(),
//            legoThemeRepository.getLegoThemes()
//        ) { list2, list3 ->
//            var theme = mutableListOf<LegoThemeDomainModel>()
//            when (list2) {
//                is DataResult.Success -> {
//                    theme.addAll(list2.data)
//                }
//                else -> {}
//            }
//            when (list3) {
//                is DataResult.Success -> {
//                    theme.addAll(list3.data)
//                }
//                is DataResult.Error -> {
//                    if (list2 is DataResult.Error) {
//                        return@combine list3
//                    }
//                }
//                else -> {}
//            }
//            DataResult.Success(mapper.toList(theme))
//        }
//    }

}