package test.sukhov.natife.ui.gifdetail

import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel
import test.sukhov.natife.R
import test.sukhov.natife.databinding.GifDetailFragmentBinding
import test.sukhov.natife.ui.base.BaseFragment

class GifDetailFragment : BaseFragment<GifDetailFragmentBinding, GifDetailViewModel>() {

    private val args: GifDetailFragmentArgs by navArgs()

    private val gifDetailAdapter by lazy { GifDetailAdapter() }

    override val viewModel: GifDetailViewModel by viewModel()

    override fun layout(): Int = R.layout.gif_detail_fragment

    override fun init() {
        initView()
        initObservers()
        initAdapter()
    }

    private fun initView() {
        setHasOptionsMenu(true)

        binding.gifViewPager.adapter = gifDetailAdapter
    }

    private fun initObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.getGifList().observe(viewLifecycleOwner, {
                gifDetailAdapter.submitData(lifecycle, it)
            })
        }
    }

    private fun initAdapter() {
        gifDetailAdapter.addLoadStateListener { loadState ->

            binding.gifViewPager.setCurrentItem(args.position, true)

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
}