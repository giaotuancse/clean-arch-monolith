package com.tuangiao.monolith.data.repository

import android.util.Log
import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.common.exception.ExceptionMapper
import com.tuangiao.monolith.common.mapper.LegoThemeDataDomainMapper
import com.tuangiao.monolith.data.datasource.LegoThemeDataSource
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LegoThemeRepository @Inject constructor(
    private val legoThemeDataSource: LegoThemeDataSource,
    private val mapper : LegoThemeDataDomainMapper,
    private val errorMapper: ExceptionMapper
) {
     suspend fun getLegoThemes(): Flow<DataResult<List<LegoThemeDomainModel>>> {
        return flow {
            try {
                val data = legoThemeDataSource.getLegoTheme()
                emit(DataResult.Success(mapper.fromList(data)))
            } catch (ex: Exception) {
                // Emit error
                emit(DataResult.Error(ex))
            }
        }
    }
}