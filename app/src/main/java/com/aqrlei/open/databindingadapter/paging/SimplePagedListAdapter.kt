package com.aqrlei.open.databindingadapter.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.aqrlei.open.databindingadapter.BindingBean

/**
 * @author aqrlei on 2019/1/22
 */
class SimplePagedListAdapter : PagedListAdapter<BindingBean, RecyclerView.ViewHolder> {
    constructor(config: AsyncDifferConfig<BindingBean>) : super(config)
    constructor(diffCallback: DiffUtil.ItemCallback<BindingBean>) : super(diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, null)
        return SimpleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        holder.itemView.findViewById<TextView>(android.R.id.text1).text = data?.name.orEmpty()
        holder.itemView.findViewById<TextView>(android.R.id.text2).text = data?.content.orEmpty()

    }

    class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}