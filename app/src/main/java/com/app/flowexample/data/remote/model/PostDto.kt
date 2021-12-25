package com.app.flowexample.data.remote.model

import com.app.flowexample.domain.model.Post

data class PostDto(
    var body: String? = null,
    var id: Int? = null,
    var title: String? = null,
    var userId: Int? = null
) {
    fun toPost() = Post(
        body = body ?: "",
        id = id ?: -1,
        title = title ?: "",
        userId = userId ?: -1
    )
}