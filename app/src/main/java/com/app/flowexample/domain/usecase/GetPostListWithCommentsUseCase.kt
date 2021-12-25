package com.app.flowexample.domain.usecase

import com.app.flowexample.common.utils.DataState
import com.app.flowexample.common.utils.State
import com.app.flowexample.domain.model.Comment
import com.app.flowexample.domain.model.Post
import com.app.flowexample.domain.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class GetPostListWithCommentsUseCase @Inject constructor(
    private val postRepository: PostRepository
){

    operator fun invoke() = flow {
        val postList = arrayListOf<Post>()

        when (val postListResponse = postRepository.getPostList()) {
            is DataState.Error -> emit(State.error(postListResponse.errorModel))
            is DataState.Success -> {
                postListResponse.data?.let {
                    postList.addAll(it)
                }
            }
        }

        postList.forEach { post ->
            val comments = arrayListOf<Comment>()

            when (val commentsResponse = postRepository.getComment(postId = post.id)) {
                is DataState.Error -> Unit
                is DataState.Success -> {
                    commentsResponse.data?.let {
                        comments.addAll(it)
                    }
                }
            }

            post.commentList = comments
        }

        emit(State.successes(postList))

    }.flowOn(Dispatchers.IO)

}