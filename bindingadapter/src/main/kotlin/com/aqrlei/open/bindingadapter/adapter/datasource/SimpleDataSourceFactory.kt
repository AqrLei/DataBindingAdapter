package com.aqrlei.open.bindingadapter.adapter.datasource

import androidx.paging.DataSource

/**
 * @author aqrlei on 2019/1/23
 */
class SimpleDataSourceFactory<T>(
    private val loadDataAction: (Int, Int) -> List<T>,
    private val type: DataSourceType?,
    private val loadPage:Int = 0
) : DataSource.Factory<Int, T>() {
    override fun create(): DataSource<Int, T> {
        return when (type) {
            DataSourceType.PAGE -> SimplePageDataSource(loadDataAction,loadPage)
            else -> SimplePositionalDataSource(loadDataAction)
        }
    }

    enum class DataSourceType {
        POSITION, PAGE
    }
}