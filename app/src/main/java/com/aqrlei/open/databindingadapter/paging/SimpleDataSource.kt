package com.aqrlei.open.databindingadapter.paging

import androidx.paging.PositionalDataSource
import com.aqrlei.open.databindingadapter.BindingBean

/**
 * @author aqrlei on 2019/1/22
 */
class SimpleDataSource(private val loadAction: (Int, Int) -> List<BindingBean?>) : PositionalDataSource<BindingBean>() {
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<BindingBean>) {
        callback.onResult(loadAction.invoke(0, 10), 1, 11)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<BindingBean>) {
        callback.onResult(loadAction.invoke(params.startPosition, 10))
    }
}