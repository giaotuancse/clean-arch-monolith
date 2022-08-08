package com.tuangiao.monolith.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T, VB : ViewBinding> constructor(viewBinding: VB) :
    RecyclerView.ViewHolder(viewBinding.root) {

    private var item: T? = null

    fun doBindings(data: T?) {
        this.item = data
    }

    abstract fun bind()

    fun getRowItem(): T? {
        return item
    }

}