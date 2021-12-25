package com.app.flowexample.domain.repository

import com.app.flowexample.common.utils.DataState
import com.app.flowexample.domain.model.Comment
import com.app.flowexample.domain.model.Post

interface PostRepository {
    suspend fun getPostList(): DataState<List<Post>>
    suspend fun getComment(postId: Int): DataState<List<Comment>>
}