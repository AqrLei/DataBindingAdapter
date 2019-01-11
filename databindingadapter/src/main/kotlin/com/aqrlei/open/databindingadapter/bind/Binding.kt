package com.aqrlei.open.databindingadapter.bind

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqrlei.open.databindingadapter.adapter.DataBindingRecyclerAdapter

/**
 * @author aqrlei on 2019/1/11
 */

@BindingAdapter(
    "android:itemBinding",
    "android:items",
    "android:layoutManager",
    "android:itemAnimator",
    requireAll = false
)
fun <T> RecyclerView.setAdapter(
    itemBinding: ItemBinding<T>,
    items: List<T>,
    layoutManager: RecyclerView.LayoutManager?,
    itemAnimator: RecyclerView.ItemAnimator? = null
) {
    val oldAdapter = this.adapter as? DataBindingRecyclerAdapter<T>
    val adapter = oldAdapter ?: DataBindingRecyclerAdapter()
    adapter.setItemBind(itemBinding)
    adapter.setItems(items)

    this.itemAnimator = itemAnimator?:this.itemAnimator
    this.layoutManager = layoutManager ?: LinearLayoutManager(this.context)
    if (adapter != oldAdapter) {
        this.adapter = adapter
    }
}