package com.tuangiao.monolith.di

import com.tuangiao.monolith.common.mapper.LegoThemeDataDomainMapper
import com.tuangiao.monolith.common.mapper.Mapper
import com.tuangiao.monolith.data.model.LegoThemeModel
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Module that holds Mappers
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {

    // data mapper
    @Binds
    abstract fun bindLegoThemeDataDomainMapper(mapper: LegoThemeDataDomainMapper): Mapper<LegoThemeModel, LegoThemeDomainModel>


}