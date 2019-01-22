package com.aqrlei.open.databindingadapter.paging

import androidx.paging.PositionalDataSource
import com.aqrlei.open.databindingadapter.BindingBean

/**
 * @author aqrlei on 2019/1/22
 */
class  SimpleDataSource:PositionalDataSource<BindingBean>(){
    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<BindingBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<BindingBean>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}