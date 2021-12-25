package com.app.flowexample.domain.model

data class Post(
    val body: String,
    val id: Int,
    val title: String,
    val userId: Int,
    var commentList: List<Comment> = arrayListOf()
)
