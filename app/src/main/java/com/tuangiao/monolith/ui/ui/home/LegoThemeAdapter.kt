package com.tuangiao.monolith.ui.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.tuangiao.monolith.databinding.ItemLegoThemeBinding
import com.tuangiao.monolith.domain.model.LegoThemeDomainModel
import com.tuangiao.monolith.presentation.model.LegoThemeUIModel
import com.tuangiao.monolith.ui.base.BaseAdapter

class LegoThemeAdapter() :
    BaseAdapter<LegoThemeUIModel, ItemLegoThemeBinding, LegoThemeViewHolder>(LegoThemeDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LegoThemeViewHolder {
        val binding = ItemLegoThemeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return LegoThemeViewHolder(binding = binding)
    }
}

class LegoThemeDiffUtil : DiffUtil.ItemCallback<LegoThemeUIModel>() {
    override fun areItemsTheSame(oldItem: LegoThemeUIModel, newItem: LegoThemeUIModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LegoThemeUIModel, newItem: LegoThemeUIModel): Boolean {
        return oldItem == newItem
    }
}