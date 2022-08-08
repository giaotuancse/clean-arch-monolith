package com.tuangiao.monolith.common.mapper


import com.tuangiao.monolith.data.model.LegoThemeModel
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import javax.inject.Inject

class LegoThemeDataDomainMapper @Inject constructor() :
    Mapper<LegoThemeModel, LegoThemeDomainModel> {

    override fun from(i: LegoThemeModel): LegoThemeDomainModel {
        return LegoThemeDomainModel(i.id, i.name, i.parentId )
    }

    override fun to(o: LegoThemeDomainModel): LegoThemeModel {
        return LegoThemeModel(o.id, o.name, o.parentId)
    }

}