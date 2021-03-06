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
import com.aqrlei.open.bindingadapter.adapter.datasource.SimpleDataSourceFactory

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
    "android:pagingDataSourceType",
    "android:currentPage",
    requireAll = false
)
fun <T> RecyclerView.setAdapter(
    itemBinding: ItemBinding<T>,
    items: List<T>?,
    layoutManager: RecyclerView.LayoutManager?,
    itemAnimator: RecyclerView.ItemAnimator?,
    usePaging: Boolean? = false,
    pagedConfig: PagedList.Config?,
    loadDataAction: ((Int, Int) -> List<T>)?,
    diffCallback: DiffUtil.ItemCallback<T>?,
    asyncDifferConfig: AsyncDifferConfig<T>?,
    dataSourceType: SimpleDataSourceFactory.DataSourceType?,
    currentPage: Int = 0

) {
    if (usePaging == true && pagedConfig != null && loadDataAction != null && (diffCallback != null || asyncDifferConfig != null)) {
        @Suppress("UNCHECKED_CAST")
        val oldAdapter = (this.adapter as? DataBindingPagingAdapter<T>)
        val adapter = oldAdapter ?: let {
            if (diffCallback != null) {
                DataBindingPagingAdapter(diffCallback, pagedConfig, loadDataAction)
            } else {
                DataBindingPagingAdapter(asyncDifferConfig!!, pagedConfig, loadDataAction)
            }
        }
        with(adapter) {
            setItemBind(itemBinding)
            setDataSourceType(dataSourceType)
            setCurrentPageNum(currentPage)
        }
        if (adapter != oldAdapter) {
            this.adapter = adapter
        }
        (this.context as? LifecycleOwner)?.run {
            adapter.observeToLoadMore(this)
        }
    } else {
        @Suppress("UNCHECKED_CAST")
        val oldAdapter = this.adapter as? DataBindingRecyclerAdapter<T>
        val adapter = oldAdapter ?: DataBindingRecyclerAdapter()
        with(adapter) {
            setItemBind(itemBinding)
            setItems(items ?: emptyList())
        }
        if (adapter != oldAdapter) {
            this.adapter = adapter
        }
    }
    this.itemAnimator = itemAnimator
    this.layoutManager = layoutManager ?: LinearLayoutManager(this.context)
}

@BindingAdapter("android:onPageRefresh")
fun RecyclerView.onPagingRefresh(refreshEvent: Boolean?) {
    if (refreshEvent == true) {
        (this.adapter as? DataBindingPagingAdapter<*>)?.let { adapter ->
            (this.context as? LifecycleOwner)?.let { lifecycleOwner ->
                adapter.refresh(lifecycleOwner)
            }
        }
    }
}