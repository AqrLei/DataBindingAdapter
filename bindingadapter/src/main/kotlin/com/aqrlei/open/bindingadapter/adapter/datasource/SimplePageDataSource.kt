package com.aqrlei.open.bindingadapter.adapter.datasource

import androidx.paging.PageKeyedDataSource

/**
 * @author aqrlei on 2019/1/25
 */
class SimplePageDataSource<T>(
    private val loadDataAction: (Int, Int) -> List<T>,
    private val curPage: Int
) : PageKeyedDataSource<Int, T>() {
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        val previousPage = if (curPage == 0) null else curPage - 1
        val nextPage = curPage + 1
        val initData = loadDataAction(curPage, nextPage)
        callback.onResult(initData, previousPage, nextPage)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val nextPage = params.key + 1
        callback.onResult(loadDataAction(params.key, nextPage), nextPage)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        val nextPage = params.key - 1
        val prePage = if (nextPage < 0) null else nextPage
        callback.onResult(loadDataAction(params.key, nextPage), prePage)
    }
}