package com.app.flowexample.data.remote.model

import com.app.flowexample.domain.model.Comment

data class CommentDto(
    var body: String? = null,
    var email: String? = null,
    var id: Int? = null,
    var name: String? = null,
    var postId: Int? = null,
) {
    fun toComment() = Comment(
        body = body ?: "",
        email = email ?: "",
        id = id ?: -1,
        name = name ?: "",
        postId = postId ?: -1,
    )
}