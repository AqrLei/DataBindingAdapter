package com.aqrlei.open.bindingadapter.bind

import androidx.databinding.BindingAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedList
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aqrlei.open.bindingadapter.adapter.DataBindingPagingAdapter
import com.aqrlei.open.bindingadapter.adapter.DataBindingRecyclerAdapter

/**
 * @author aqrlei on 2019/1/11
 */

@BindingAdapter(
    "android:itemBinding",
    "android:items",
    "android:layoutManager",
    "android:itemAnimator",
    "android:usePaging",
    "android:pagedConfig",
    "android:onLoadData",
    "android:diffCallback",
    "android:asyncDifferConfig",
    requireAll = false
)
fun <T> RecyclerView.setAdapter(
    itemBinding: ItemBinding<T>,
    items: List<T>?,
    layoutManager: RecyclerView.LayoutManager?,
    itemAnimator: RecyclerView.ItemAnimator?,
    usePaging: Boolean = false,
    pagedConfig: PagedList.Config?,
    loadDataAction: ((Int, Int) -> List<T>)?,
    diffCallback: DiffUtil.ItemCallback<T>?,
    asyncDifferConfig: AsyncDifferConfig<T>?

) {
    if (usePaging && pagedConfig != null && loadDataAction != null && (diffCallback != null || asyncDifferConfig != null)) {
        val oldAdapter = (this.adapter as? DataBindingPagingAdapter<T>)
        val adapter = oldAdapter ?: let {
            if (diffCallback != null) {
                DataBindingPagingAdapter(diffCallback, pagedConfig, loadDataAction)
            } else {
                DataBindingPagingAdapter(asyncDifferConfig!!, pagedConfig, loadDataAction)
            }
        }
        adapter.setItemBind(itemBinding)
        if (adapter != oldAdapter) {
            this.adapter = adapter
        }
        (this.context as? LifecycleOwner)?.run {
            adapter.observeToLoadMore(this)
        }
    } else {
        val oldAdapter = this.adapter as? DataBindingRecyclerAdapter<T>
        val adapter = oldAdapter ?: DataBindingRecyclerAdapter()
        adapter.setItemBind(itemBinding)
        adapter.setItems(items ?: emptyList())
        if (adapter != oldAdapter) {
            this.adapter = adapter
        }
    }
    this.itemAnimator = itemAnimator
    this.layoutManager = layoutManager ?: LinearLayoutManager(this.context)
}

@BindingAdapter("android:onPageRefresh")
fun RecyclerView.onPagingRefresh(refreshEvent: Any?) {
    if (refreshEvent != null) {
        (this.adapter as? DataBindingPagingAdapter<*>)?.let { adapter ->
            (this.context as? LifecycleOwner)?.let { lifecycleOwner ->
                adapter.refresh(lifecycleOwner)
            }
        }
    }
}