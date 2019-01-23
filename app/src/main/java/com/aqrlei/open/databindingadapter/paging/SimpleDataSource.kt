package com.aqrlei.open.databindingadapter.paging

import android.util.Log
import androidx.paging.PositionalDataSource
import com.aqrlei.open.databindingadapter.BindingBean

/**
 * @author aqrlei on 2019/1/22
 */
class SimpleDataSource(private val loadAction: (Int) -> List<BindingBean>) : PositionalDataSource<BindingBean>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<BindingBean>) {
        Log.d("paging", "loadInitial: ${params.requestedLoadSize}-${params.requestedStartPosition}")
        val initData = loadAction.invoke(0)
        val totalCount = initData.size
        callback.onResult(loadAction.invoke(0), 0, totalCount)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<BindingBean>) {
        Log.d("paging", "loadRange: ${params.loadSize}-${params.startPosition}")
        callback.onResult(loadAction.invoke(params.startPosition))
    }
}