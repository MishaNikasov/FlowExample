package com.app.flowexample.presentation.fragment.main

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.app.flowexample.common.extensions.collectWhenStarted
import com.app.flowexample.databinding.FragmentMainBinding
import com.app.flowexample.domain.model.Post
import com.app.flowexample.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList

@AndroidEntryPoint
class MainFragment: BaseFragment<FragmentMainBinding>(FragmentMainBinding::inflate) {

    private val viewModel: MainViewModel by viewModels()

    override fun setupViewModelCallbacks() {
        viewModel.postList.collectWhenStarted(this) { state ->
            state.getResult(
                loading = {
                    loadingState(true)
                },
                error = { errorModel ->
                    loadingState(false)
                    errorState(errorModel)
                },
                successes = { postList ->
                    setupPostList(postList)
                    loadingState(false)
                }
            )
        }
    }

    private fun setupPostList(postList: ArrayList<Post>) {
        var text = ""
        postList.forEach {
            text += "${it.title} ${it.commentList.joinToString { comment -> comment.name }}\n"
        }
        requireBinding().sharedTextField.text = text
    }

    override fun loadingState(isLoading: Boolean) {
        requireBinding().progress.isVisible = isLoading
    }

}