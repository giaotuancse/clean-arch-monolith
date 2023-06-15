package com.tuangiao.monolith.utils

import com.tuangiao.monolith.data.model.LegoThemeModel
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel


class TestDataGenerator {
    companion object {
        fun generateLegoThemesDataModel() : List<LegoThemeModel> {
            val item1 = LegoThemeModel(1, "title 1", 2)
            val item2 = LegoThemeModel(2, "title 2", 1)
            val item3 = LegoThemeModel(3 ,"title 3", 1)
            return listOf(item1, item2, item3)
        }
    }
}