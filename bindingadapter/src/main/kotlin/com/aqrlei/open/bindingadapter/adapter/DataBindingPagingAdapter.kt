package com.aqrlei.open.bindingadapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.OnRebindCallback
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aqrlei.open.bindingadapter.adapter.datasource.SimpleDataSourceFactory
import com.aqrlei.open.bindingadapter.bind.ItemBinding

/**
 * @author aqrlei on 2019/1/21
 */

class DataBindingPagingAdapter<T> : PagedListAdapter<T, RecyclerView.ViewHolder>, DataBindingAdapter<T> {

    private val pagedList: LiveData<PagedList<T>>
        get() = LivePagedListBuilder<Int, T>(
            SimpleDataSourceFactory(loadDataAction),
            config
        ).build()

    private lateinit var itemBinding: ItemBinding<T>
    private val config: PagedList.Config
    private val loadDataAction: (Int, Int) -> List<T>
    private var containerView: RecyclerView? = null

    constructor(
        asyncDifferConfig: AsyncDifferConfig<T>,
        config: PagedList.Config,
        loadDataAction: (Int, Int) -> List<T>
    ) : super(asyncDifferConfig) {
        this.config = config
        this.loadDataAction = loadDataAction

    }

    constructor(
        diffCallback: DiffUtil.ItemCallback<T>,
        config: PagedList.Config,
        loadDataAction: (Int, Int) -> List<T>
    ) : super(diffCallback) {
        this.config = config
        this.loadDataAction = loadDataAction
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        containerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        containerView = null
    }

    override fun setItemBind(itemBinding: ItemBinding<T>) {
        this.itemBinding = itemBinding
    }

    fun observeToLoadMore(lifecycleOwner: LifecycleOwner) {
        pagedList.observe(lifecycleOwner, Observer {
            this.submitList(it)
        })
    }

    fun refresh(lifecycleOwner: LifecycleOwner) {
        this.submitList(null)
        observeToLoadMore(lifecycleOwner)
    }

    override fun getItemViewType(position: Int): Int {
        getItem(position)?.run {
            itemBinding.itemBind(position, this)
        }
        return itemBinding.layoutRes
    }

    override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): RecyclerView.ViewHolder {
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(LayoutInflater.from(parent.context), layoutId, parent, false)
        val holder =
            DataBindingViewHolder(binding.root)
        binding.addOnRebindCallback(object : OnRebindCallback<ViewDataBinding>() {

            override fun onPreBind(binding: ViewDataBinding): Boolean {
                return containerView?.isComputingLayout == true
            }

            override fun onCanceled(binding: ViewDataBinding) {
                if (containerView == null || containerView?.isComputingLayout == true) {
                    return
                }
                val position = holder.adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    notifyItemChanged(position, Any())
                }
            }
        })
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = DataBindingUtil.getBinding<ViewDataBinding>(holder.itemView)
        getItem(position)?.run {
            if (itemBinding.bind(binding, this)) {
                binding?.executePendingBindings()
            }
        }
    }
}