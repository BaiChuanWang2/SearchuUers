package com.example.searchusers.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.searchusers.data.model.UserModel
import com.example.searchusers.databinding.AdapterSearchBinding
import com.example.searchusers.viewmodel.SearchViewModel

class SearchAdapter constructor(context: Context, private val viewModel: SearchViewModel) : PagingDataAdapter<UserModel, SearchAdapter.ViewHolder>(DiffCallback) {
    private val inflater = LayoutInflater.from(context)

    class ViewHolder constructor(private val binding: AdapterSearchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(viewModel: SearchViewModel, dataModel: UserModel) {
            binding.apply {
                this.viewModel = viewModel
                this.dataModel = dataModel
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdapterSearchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.apply {
            holder.bindView(viewModel, this)
        }
    }

    object DiffCallback: DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
}