package com.tuangiao.monolith.domain.mapper


import com.tuangiao.monolith.common.DataResult
import com.tuangiao.monolith.common.mapper.Mapper
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import com.tuangiao.monolith.presentation.model.LegoThemeUIModel
import javax.inject.Inject

class LegoThemeDomainUIMapper @Inject constructor() :
    Mapper<LegoThemeDomainModel, LegoThemeUIModel> {

    override fun from(i: LegoThemeDomainModel): LegoThemeUIModel {
        return LegoThemeUIModel(i.id, i.name, i.parentId )
    }

    override fun to(o: LegoThemeUIModel): LegoThemeDomainModel {
        return LegoThemeDomainModel(o.id, o.name, o.parentId)
    }

    fun transformDataResultFrom(input : DataResult<List<LegoThemeDomainModel>>): DataResult<List<LegoThemeUIModel>> {
        when (input) {
            is DataResult.Error -> return DataResult.Error(input.exception)
            is DataResult.Loading -> return DataResult.Loading
            is DataResult.Success -> return DataResult.Success(fromList(input.data))
        }


    }
}