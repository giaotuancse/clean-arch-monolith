package com.tuangiao.monolith.data.datasource

import com.tuangiao.monolith.data.model.LegoThemeModel
import com.tuangiao.monolith.network.LegoServices
import javax.inject.Inject

class LegoThemeDataSource @Inject constructor(
    private val legoServices: LegoServices
) {
    suspend fun getLegoTheme() : List<LegoThemeModel> = legoServices.getThemes().results
}