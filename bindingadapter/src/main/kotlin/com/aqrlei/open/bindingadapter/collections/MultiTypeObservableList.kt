package com.aqrlei.open.bindingadapter.collections

import androidx.databinding.ListChangeRegistry
import androidx.databinding.ObservableList
import java.util.AbstractList
import kotlin.collections.ArrayList

/**
 * @author aqrlei on 2019/1/14
 * Collections.singletonList 只有一个item时的内存优化"
 */
class MultiTypeObservableList<T> : AbstractList<T>(), ObservableList<T> {
    private val listeners = ListChangeRegistry()
    private val callback = ListChangeCallBack<T>()
    private val lists: ArrayList<List<out T>> = ArrayList()
    override fun removeOnListChangedCallback(callback: ObservableList.OnListChangedCallback<out ObservableList<T>>) {
        listeners.remove(callback)
    }

    override fun addOnListChangedCallback(callback: ObservableList.OnListChangedCallback<out ObservableList<T>>?) {
        listeners.add(callback)
    }

    override val size: Int
        get() {
            var tempSize = 0
            for (list in lists) {
                tempSize += list.size
            }
            return tempSize
        }

    override fun get(index: Int): T {
        if (index < 0) {
            throw IndexOutOfBoundsException()
        }
        var tempSize = 0
        for (list in lists) {
            if (index - tempSize < list.size) {
                return list[index - tempSize]
            }
            tempSize += list.size
        }
        throw java.lang.IndexOutOfBoundsException()
    }

    inner class ListChangeCallBack<T> : ObservableList.OnListChangedCallback<ObservableList<T>>() {
        override fun onChanged(sender: ObservableList<T>?) {
            modCount++
            listeners.notifyChanged(this@MultiTypeObservableList)
        }

        override fun onItemRangeRemoved(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemRangeMoved(sender: ObservableList<T>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemRangeInserted(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onItemRangeChanged(sender: ObservableList<T>?, positionStart: Int, itemCount: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }


    }
}