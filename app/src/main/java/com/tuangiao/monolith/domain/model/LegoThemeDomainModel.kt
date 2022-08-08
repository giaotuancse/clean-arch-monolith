package com.tuangiao.monolith.domain.model

data class LegoThemeDomainModel(
    val id: Int, val name: String,
    val parentId: Int?
) {
}