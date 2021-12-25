package com.app.flowexample.data.repository

import com.app.flowexample.common.utils.DataState
import com.app.flowexample.data.remote.NetworkApi
import com.app.flowexample.domain.model.Comment
import com.app.flowexample.domain.model.Post
import com.app.flowexample.domain.repository.PostRepository
import com.app.flowexample.presentation.base.BaseRepository
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val networkApi: NetworkApi
): BaseRepository(), PostRepository {

    override suspend fun getPostList(): DataState<List<Post>> {
        return obtainResponse(
            request = networkApi.getPostList(),
            mapper = { list ->
                list?.mapNotNull { item -> item?.toPost() } ?: arrayListOf()
            }
        )
    }

    override suspend fun getComment(postId: Int): DataState<List<Comment>> {
        return obtainResponse(
            request = networkApi.getComments(postId),
            mapper = { list ->
                list?.mapNotNull { item -> item?.toComment() } ?: arrayListOf()
            }
        )
    }
}