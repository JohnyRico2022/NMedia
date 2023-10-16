package ru.netology.nmedia.dao

import android.content.Context
import ru.netology.nmedia.dto.Post


interface PostDao {

    fun getAll(): List<Post>
    fun save(post: Post, context: Context): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun share(id: Long)

}