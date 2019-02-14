package com.aqrlei.open.databindingadapter

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import androidx.recyclerview.widget.DiffUtil
import com.aqrlei.open.bindingadapter.adapter.datasource.SimpleDataSourceFactory
import com.aqrlei.open.bindingadapter.bind.ItemBinding
import com.aqrlei.open.bindingadapter.collections.MultiTypeObservableList
import com.aqrlei.open.databindingadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    val items = ObservableArrayList<String>()
    val mutItems = ObservableArrayList<Any>()
    val mutListItems = MultiTypeObservableList<Any>()
        .insertItem("Header")
        .insertList(mutItems)
        .insertItem("Footer")
    val refreshEvent = ObservableBoolean()
    private lateinit var binding: ActivityMainBinding
    val dataSourceType = SimpleDataSourceFactory.DataSourceType.PAGE
    val currentPage = 5
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        val itemBinding = ItemBinding.create<String>().set(BR.item, R.layout.list_item_binding)
        val mutItemBinding = ItemBinding.create<Any>()
            .add(BR.item, R.layout.list_item_binding, String::class.java)
            .add(BR.item, R.layout.list_item_mut, BindingBean::class.java)
        val pagingItemBinding = ItemBinding.create<BindingBean>().set(BR.item, R.layout.list_item_mut)

        binding.itemBinding = mutItemBinding
        binding.items = mutListItems
        binding.activity = this

        binding.config = PagedList.Config.Builder()
            .setPageSize(10)
            .setEnablePlaceholders(false)
            .build()
        binding.diffCallback = object : DiffUtil.ItemCallback<BindingBean>() {
            override fun areContentsTheSame(oldItem: BindingBean, newItem: BindingBean): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: BindingBean, newItem: BindingBean): Boolean {
                return oldItem == newItem
            }
        }
        binding.usePaging = true

    }

    val loadDataAction = { startPosition: Int, loadSize: Int ->
        loadData(startPosition, loadSize)
    }

    private fun loadData(startPosition: Int, loadSize: Int): List<Any> {
        Log.d("Paging", "loadData: $startPosition-$loadSize")
        val list = ArrayList<Any>()
        if (startPosition <= 6) {
            for (i in 0 until 10) {
                list.add(
                    if (i % 2 == 0)
                        BindingBean().apply {
                            id = startPosition + i
                            name = "$id 测试"
                            content = "测试：页-$startPosition 序号-$i"
                        } else "start: $startPosition, offset: $i")
            }
        }
        return list
    }

    fun onFabClick(view: View) {
        refreshEvent.set(true)
        /*  simplePagingAdapter.submitList(null)
          respData.observe(this, Observer<PagedList<BindingBean>> {
              simplePagingAdapter.submitList(it)
          })*/
        /*if (++position % 2 == 0) {
            mutItems.add(BindingBean(0, "name", "content", "des"))
        } else {
            mutItems.add("string")
        }*/
        //  items.add("test")
    }


}
