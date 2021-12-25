package com.app.flowexample.data.remote

import com.app.flowexample.data.remote.model.CommentDto
import com.app.flowexample.data.remote.model.PostDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetworkApi {

    @GET(NetworkUrl.POSTS)
    suspend fun getPostList(): Response<List<PostDto?>?>

    @GET(NetworkUrl.COMMENTS)
    suspend fun getComments(
        @Path ("postId") postId: Int
    ): Response<List<CommentDto?>?>
}