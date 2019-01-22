package com.aqrlei.open.databindingadapter.paging

import androidx.paging.DataSource
import com.aqrlei.open.databindingadapter.BindingBean

/**
 * @author aqrlei on 2019/1/22
 */
class SimpleDataSourceFactory : DataSource.Factory<Int, BindingBean>() {
    override fun create(): DataSource<Int, BindingBean> {
        return SimpleDataSource()
    }
}