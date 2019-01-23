package com.aqrlei.open.bindingadapter.adapter

import com.aqrlei.open.bindingadapter.bind.ItemBinding

/**
 * @author aqrlei on 2019/1/23
 */
interface DataBindingAdapter<T> {
    fun setItemBind(itemBinding: ItemBinding<T>)
}