package test.sukhov.natife.ui.giflist

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import test.sukhov.natife.R
import test.sukhov.natife.databinding.GifListFragmentBinding
import test.sukhov.natife.ui.base.BaseFragment
import test.sukhov.natife.utils.Utils.hideKeyboard

class GifListFragment : BaseFragment<GifListFragmentBinding, GifListViewModel>() {

    companion object {
        private const val START_POSITION = 0
    }

    private val gifListAdapter by lazy {
        GifListAdapter {
            findNavController()
                .navigate(
                    GifListFragmentDirections
                        .actionGifListFragmentToGifDetailFragment(it)
                )
        }
    }

    override val viewModel: GifListViewModel by viewModel()

    override fun layout(): Int = R.layout.gif_list_fragment

    override fun init() {
        initView()
        initObservers()
        initAdapter()
        bindEvents()
    }

    private fun initView() {
        setHasOptionsMenu(true)

        binding.gifRecyclerView.adapter = gifListAdapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getGifList().observe(viewLifecycleOwner, {
                gifListAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun initAdapter() {
        gifListAdapter.addLoadStateListener { loadState ->

            val isListEmpty = loadState.refresh is LoadState.NotLoading && gifListAdapter.itemCount == 0
            binding.tvNoResults.isVisible = isListEmpty

            binding.gifRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading

            handleLoading(loadState.source.refresh is LoadState.Loading)

            binding.btnRetry.isVisible = loadState.source.refresh is LoadState.Error

            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.let {
                showToastMessage(it.error.message.toString())
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.e("LOG", "onSaveInstanceState")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.e("LOG", "onViewStateRestored")
    }

    private fun bindEvents() {
        with(binding) {
            gifRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    fabListUp.isVisible = dy <= START_POSITION

                    val scrollPosition =
                        (recyclerView.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()
                    refreshLayout.isEnabled = scrollPosition == START_POSITION
                }
            })

            fabListUp.setOnClickListener { gifRecyclerView.smoothScrollToPosition(START_POSITION) }

            refreshLayout.setOnRefreshListener { gifListAdapter.refresh() }

            btnRetry.setOnClickListener { gifListAdapter.retry() }
        }
    }

    private fun handleLoading(loading: Boolean) {
        binding.refreshLayout.isRefreshing = loading == true
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.gif_list_menu, menu)

        val searchView = menu.findItem(R.id.gif_list_search).actionView as SearchView
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }

            override fun onQueryTextSubmit(query: String): Boolean {


                hideKeyboard()
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.gif_list_search -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}