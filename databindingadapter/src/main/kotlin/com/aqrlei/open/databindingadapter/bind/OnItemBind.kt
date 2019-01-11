package com.aqrlei.open.databindingadapter.bind

/**
 * @author aqrlei on 2019/1/11
 */
interface OnItemBind<T> {
    fun onItemBind(itemBinding: ItemBinding<T>, position: Int, item: T)
}