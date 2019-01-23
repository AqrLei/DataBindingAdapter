package com.aqrlei.open.bindingadapter.adapter.datasource

import androidx.paging.PositionalDataSource

/**
 * @author aqrlei on 2019/1/23
 */
class SimplePositionalDataSource<T>(private val loadDataAction: (Int, Int) -> List<T>) : PositionalDataSource<T>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        val initData = loadDataAction(params.requestedStartPosition, params.requestedLoadSize)
        val totalCount = initData.size
        callback.onResult(initData, params.requestedStartPosition, totalCount)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        callback.onResult(loadDataAction(params.startPosition, params.loadSize))
    }
}