package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun shareCounter(id: Long)

    fun getAllAsync(callback: RepositoryCallback<List<Post>>)
    fun likeByIdAsync(id: Long, callback: RepositoryCallback<Post>)
    fun disLikeByIdAsync(id: Long, callback: RepositoryCallback<Post>)
    fun removeByIdAsync(id: Long, callback: RepositoryCallback<Post>)
    fun saveAsync(post: Post, callback: RepositoryCallback<Post>)

    interface RepositoryCallback<T>{
        fun onSuccess(result: T)
        fun onError(e: Exception)
    }
}


