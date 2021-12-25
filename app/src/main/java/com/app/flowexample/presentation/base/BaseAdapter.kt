package com.app.flowexample.presentation.base

import android.annotation.SuppressLint
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

@SuppressLint("DiffUtilEquals")
abstract class BaseAdapter<T, V: RecyclerView.ViewHolder> : RecyclerView.Adapter<V>() {

    abstract val differ: AsyncListDiffer<T>

    protected val callback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) = oldItem == newItem
        override fun areContentsTheSame(oldItem: T, newItem: T) = oldItem == newItem
    }

    protected val list: List<T>
        get() = differ.currentList

    fun submitList(list: List<T>?) {
        if (list == null) return
        differ.submitList(list)
    }

    override fun getItemCount() = list.size

}
