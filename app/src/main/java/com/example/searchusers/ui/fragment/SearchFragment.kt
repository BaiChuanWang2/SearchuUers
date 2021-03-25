package com.example.searchusers.ui.fragment

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.searchusers.databinding.FragmentSearchBinding
import com.example.searchusers.ui.adapter.SearchAdapter
import com.example.searchusers.ui.adapter.loadstate.CommonLoadStateAdapter
import com.example.searchusers.viewmodel.SearchViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by viewModel()
    private val adapter by lazy { SearchAdapter(requireContext(), viewModel) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return FragmentSearchBinding.inflate(inflater).let {
            binding = it
            binding.viewModel = viewModel
            binding.lifecycleOwner = this
            binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    @OptIn(FlowPreview::class)
    private fun initUI() {
        binding.apply {
            rv.layoutManager = LinearLayoutManager(requireContext())
            rv.adapter = adapter.withLoadStateFooter(CommonLoadStateAdapter(requireContext(), adapter))

            ev.setOnKeyListener { _, keyCode, event ->
                when {
                    event.action == KeyEvent.ACTION_DOWN &&
                            keyCode == KeyEvent.KEYCODE_ENTER -> {
                        hideKeyboard(ev)
                        if (!viewModel!!.displaySearchText.value.isNullOrEmpty()) {
                            viewModel!!.searchClick()
                        }
                        true
                    }
                    else -> false
                }
            }
            adapter.addLoadStateListener {
                loadStatesListener(it)
            }
        }
        viewModel.apply {
            lifecycleScope.launch {
                viewModel.usersList.collect {
                    adapter.submitData(it)
                }
            }
            displaySearchText.observe(viewLifecycleOwner, {
                binding.ivClear.visibility = when {
                    it.isEmpty() -> {
                        lifecycleScope.launch {
                            adapter.submitData(lifecycle, PagingData.empty())
                        }
                        View.GONE
                    }
                    else -> View.VISIBLE
                }
            })
        }
    }

    private fun loadStatesListener(loadStates: CombinedLoadStates) {
        binding.apply {
            when (loadStates.refresh) {
                is LoadState.Loading -> {
                    pb.visibility = View.VISIBLE
                }
                else -> {
                    pb.visibility = View.GONE
                    val error = when {
                        loadStates.prepend is LoadState.Error -> loadStates.prepend as LoadState.Error
                        loadStates.append is LoadState.Error -> loadStates.append as LoadState.Error
                        loadStates.refresh is LoadState.Error -> loadStates.refresh as LoadState.Error
                        else -> null
                    }
                    error?.apply {
                        if (viewModel!!.displaySearchText.value!!.isNotEmpty()) {
                            Toast.makeText(requireContext(), toString(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }

            when {
                loadStates.source.refresh is LoadState.NotLoading &&
                        loadStates.append.endOfPaginationReached &&
                        adapter.itemCount < 1 -> {
                    ivEmpty.visibility = View.VISIBLE
                    tvEmpty.visibility = View.VISIBLE
                }
                else -> {
                    ivEmpty.visibility = View.GONE
                    tvEmpty.visibility = View.GONE
                }
            }
        }
    }
}