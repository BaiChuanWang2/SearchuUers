package com.example.searchusers.ui.adapter.loadstate

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.searchusers.databinding.AdapterCommonLoadStateBinding
import com.example.searchusers.ui.adapter.SearchAdapter

class CommonLoadStateAdapter constructor(context: Context, private val adapter: SearchAdapter) : LoadStateAdapter<CommonLoadStateAdapter.ViewHolder>() {
    private val inflater = LayoutInflater.from(context)

    class ViewHolder constructor(private val binding: AdapterCommonLoadStateBinding, private val adapter: SearchAdapter) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(loadState: LoadState) {
            binding.apply {
                pb.isVisible = loadState is LoadState.Loading
                ivError.isVisible = loadState is LoadState.Error
                tvError.isVisible = loadState is LoadState.Error
                ivRetry.isVisible = loadState is LoadState.Error
                ivRetry.setOnClickListener {
                    adapter.retry()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): ViewHolder {
        val binding = AdapterCommonLoadStateBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, adapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, loadState: LoadState) {
        holder.bindView(loadState)
    }
}