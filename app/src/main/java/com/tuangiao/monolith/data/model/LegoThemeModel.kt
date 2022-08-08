package com.tuangiao.monolith.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LegoThemeModel(
    val id: Int, val name: String,
    @SerialName("parent_id")
    val parentId: Int?
) {
    override fun toString() = name
}