package test.sukhov.natife.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import test.sukhov.natife.BR

abstract class BaseFragment<Binding : ViewDataBinding, ViewModel : BaseViewModel> : Fragment() {
    protected abstract val viewModel: ViewModel
    protected lateinit var binding: Binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layout(), container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.setVariable(
            BR.viewModel,
            viewModel
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    @LayoutRes
    abstract fun layout(): Int

    abstract fun init()

    protected open fun showToastMessage(message: String?) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_LONG).show()
    }
}