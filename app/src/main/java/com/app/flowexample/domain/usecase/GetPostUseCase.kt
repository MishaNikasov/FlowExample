package com.app.flowexample.domain.usecase

import com.app.flowexample.common.utils.DataState
import com.app.flowexample.domain.model.Post
import com.app.flowexample.domain.repository.PostRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import javax.inject.Inject

class GetPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {

    suspend operator fun invoke(): DataState<List<Post>> {
        return postRepository.getPostList()
    }

}