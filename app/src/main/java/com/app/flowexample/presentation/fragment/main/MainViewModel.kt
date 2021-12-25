package com.app.flowexample.presentation.fragment.main

import androidx.lifecycle.viewModelScope
import com.app.flowexample.common.utils.State
import com.app.flowexample.domain.usecase.GetPostListWithCommentsUseCase
import com.app.flowexample.presentation.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getPostListWithCommentsUseCase: GetPostListWithCommentsUseCase
): BaseViewModel() {

   val postList = getPostListWithCommentsUseCase().stateIn(
       viewModelScope, SharingStarted.Lazily, State.loading()
   )

}