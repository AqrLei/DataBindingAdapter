package com.aqrlei.open.databindingadapter

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import com.aqrlei.open.bindingadapter.bind.ItemBinding
import com.aqrlei.open.databindingadapter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val items = ObservableArrayList<String>()
    val mutItems = ObservableArrayList<Any>()
    var position: Int = -1
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.setLifecycleOwner(this)
        val itemBinding = ItemBinding.create<String>().set(BR.item, R.layout.list_item_binding)
        val mutItemBinding = ItemBinding.create<Any>().add(BR.item, R.layout.list_item_binding, String::class.java)
            .add(BR.item, R.layout.list_item_mut, BindingBean::class.java)
        binding.itemBinding = mutItemBinding
        binding.items = mutItems

    }

    fun onFabClick(view: View) {
        if (++position % 2 == 0) {
            mutItems.add(BindingBean("name", "content", "des"))
        } else {
            mutItems.add("string")
        }
        //  items.add("test")
    }
}
