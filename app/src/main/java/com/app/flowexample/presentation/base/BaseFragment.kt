package com.app.flowexample.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import com.app.flowexample.common.extensions.showToast
import com.app.flowexample.common.utils.ErrorModel
import com.nikasov.cleanarchitectureapp.common.extensions.hideKeyboard
import timber.log.Timber

open class BaseFragment<FragmentBinding : ViewBinding>(
    private val binder: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBinding
) : Fragment() {

    private var binding: FragmentBinding? = null

    protected fun requireBinding(): FragmentBinding {
        return checkNotNull(binding)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = binder(inflater, container, false)
        this.binding = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModelCallbacks()
        setData()
        getData()
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideKeyboard()
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }


    private fun handleLostConnection() {
        Timber.d("handleLostConnection ${this.binding}")
    }

    protected fun handleBackPress(lifecycleOwner: LifecycleOwner, action: () -> Unit) {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(
                lifecycleOwner
            ) {
                action()
            }
    }

    protected open fun errorState(errorModel: ErrorModel) {
        hideKeyboard()
        loadingState(false)
        Timber.e("Error: ${errorModel.getErrorMessage()}")
        requireContext().showToast(errorModel.getErrorMessage())
    }

    protected open fun setData() {}

    protected open fun getData() {}

    protected open fun setupViews() {}

    protected open fun setupViewModelCallbacks() {}

    protected open fun loadingState(isLoading: Boolean) {}

    protected open fun refresh() {}

    protected open fun backPressAction() {}

}