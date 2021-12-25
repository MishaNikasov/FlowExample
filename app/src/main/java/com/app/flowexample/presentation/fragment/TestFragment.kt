package com.app.flowexample.presentation.fragment

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.app.flowexample.R
import com.app.flowexample.common.extensions.collectWhenStarted
import com.app.flowexample.common.extensions.showToast
import com.app.flowexample.databinding.FragmentMainBinding
import com.app.flowexample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

//    private val viewModelSharedFlow: SharedFlowTestViewModel by viewModels()
    private val stateFlowViewModel: StateFlowViewModel by viewModels()
    private val flowViewModel: FlowViewModel by viewModels()

    override fun setupViews() {
        with(requireBinding()) {
            sharedBtn.setOnClickListener {
                stateFlowViewModel.sendSharedEvent()
            }
            stateBtn.setOnClickListener {
                stateFlowViewModel.sendStateEvent()
            }
            nextScreenBtn.setOnClickListener {
                findNavController().navigate(R.id.secondFragment)
            }
        }
    }

    override fun setupViewModelCallbacks() {
        with(stateFlowViewModel) {
            stateFlowUi.collectWhenStarted(this@TestFragment) { event ->
                event.getResult {
                    requireBinding().stateTextField.text = it.text
                    requireContext().showToast(it.text)
                }
            }
            sharedFlowUi.collectWhenStarted(this@TestFragment) { event ->
                event.getResult {
                    requireBinding().sharedTextField.text = it.text
                    requireContext().showToast(it.text)
                }
            }
        }
//        with(viewModelSharedFlow) {}
        with(flowViewModel) {

        }
    }

    override fun loadingState(isLoading: Boolean) {
        requireBinding().progress.isVisible = isLoading
    }

}