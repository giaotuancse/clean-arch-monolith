package com.tuangiao.monolith.ui.ui.home

import com.tuangiao.monolith.databinding.ItemLegoThemeBinding
import com.tuangiao.monolith.presentation.model.LegoThemeUIModel
import com.tuangiao.monolith.ui.base.BaseViewHolder

class LegoThemeViewHolder constructor(
    private val binding: ItemLegoThemeBinding
) : BaseViewHolder<LegoThemeUIModel, ItemLegoThemeBinding>(binding) {

    init {
    }

    override fun bind() {
       getRowItem()?.let {
           binding.tvLegoName.text = it.name

       }
    }

}