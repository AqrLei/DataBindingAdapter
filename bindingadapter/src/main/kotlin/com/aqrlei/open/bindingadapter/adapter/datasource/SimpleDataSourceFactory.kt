package com.aqrlei.open.bindingadapter.adapter.datasource

import androidx.paging.DataSource

/**
 * @author aqrlei on 2019/1/23
 */
class SimpleDataSourceFactory<T>(private val loadDataAction: (Int, Int) -> List<T>) : DataSource.Factory<Int, T>() {
    override fun create(): DataSource<Int, T> {
        return SimplePositionalDataSource(loadDataAction)
    }
}