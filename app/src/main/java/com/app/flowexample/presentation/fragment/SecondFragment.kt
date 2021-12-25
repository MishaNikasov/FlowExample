package com.app.flowexample.presentation.fragment

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.app.flowexample.common.extensions.collectWhenStarted
import com.app.flowexample.common.utils.State
import com.app.flowexample.databinding.FragmentSecondBinding
import com.app.flowexample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentSecondBinding>(FragmentSecondBinding::inflate) {

    private val flowViewModel: FlowViewModel by viewModels()

    override fun setupViews() {
        with(requireBinding()) {
            editField.doAfterTextChanged {
                flowViewModel.search(it.toString())
            }
        }
    }

    override fun setupViewModelCallbacks() {
        with(flowViewModel) {
            searchResult.collectWhenStarted(this@SecondFragment) { state ->
                if (state is State.Loading) {
                    loadingState(true)
                }
                state.getResult {
                    loadingState(false)
                    Timber.i(it)
                    setTextField(it)
                }
            }
        }
    }

    private fun setTextField(it: String) {
        requireBinding().textField.text = it
    }

    override fun loadingState(isLoading: Boolean) {
        requireBinding().progress.isVisible = isLoading
    }
}