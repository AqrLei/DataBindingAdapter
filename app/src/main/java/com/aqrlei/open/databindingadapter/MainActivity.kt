package com.aqrlei.open.databindingadapter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.aqrlei.open.bindingadapter.bind.ItemBinding
import com.aqrlei.open.bindingadapter.collections.MultiTypeObservableList
import com.aqrlei.open.databindingadapter.databinding.ActivityMainBinding
import com.aqrlei.open.databindingadapter.paging.SimpleDataSourceFactory
import com.aqrlei.open.databindingadapter.paging.SimplePagedListAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    val items = ObservableArrayList<String>()
    val mutItems = ObservableArrayList<Any>()
    val mutListItems = MultiTypeObservableList<Any>()
        .insertItem("Header")
        .insertList(mutItems)
        .insertItem("Footer")
    var position: Int = -1
    private lateinit var binding: ActivityMainBinding

    private val simplePagingAdapter = SimplePagedListAdapter(object : DiffUtil.ItemCallback<BindingBean>() {
        override fun areContentsTheSame(oldItem: BindingBean, newItem: BindingBean): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: BindingBean, newItem: BindingBean): Boolean {
            return oldItem == newItem

        }
    })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        val itemBinding = ItemBinding.create<String>().set(BR.item, R.layout.list_item_binding)
        val mutItemBinding = ItemBinding.create<Any>().add(BR.item, R.layout.list_item_binding, String::class.java)
            .add(BR.item, R.layout.list_item_mut, BindingBean::class.java)
        binding.itemBinding = mutItemBinding
        binding.items = mutListItems

        val respData = LivePagedListBuilder<Int, BindingBean>(
            SimpleDataSourceFactory { startPos, size ->
                loadData(startPos, size)
            }, PagedList.Config.Builder()
                .setPageSize(10)
                .setEnablePlaceholders(false)
                .setInitialLoadSizeHint(10)
                .build()
        ).build()
        respData.observe(this, Observer<PagedList<BindingBean>> {
            simplePagingAdapter.submitList(it)
        })
        pagingRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = simplePagingAdapter
        }

    }

    private fun loadData(startPosition: Int, loadSize: Int): List<BindingBean> {
        if (startPosition > 100) {
            return emptyList()
        }
        val list = ArrayList<BindingBean>()
        for (i in 0 until loadSize) {
            list.add(BindingBean().apply {
                id = startPosition + i
                name = "$id 测试"
                content = "测试：$id"
            })
        }
        return list
    }

    fun onFabClick(view: View) {
        if (++position % 2 == 0) {
            mutItems.add(BindingBean(0, "name", "content", "des"))
        } else {
            mutItems.add("string")
        }
        //  items.add("test")
    }


}
