package ru.netology.nmedia.repository

import ru.netology.nmedia.dto.Post

interface PostRepository {
    fun shareCounter(id: Long)

    fun getAllAsync(callback: RepositoryCallback<List<Post>>)
    fun likeByIdAsync(id: Long, callback: RepositoryCallback<Post>)
    fun disLikeByIdAsync(id: Long, callback: RepositoryCallback<Post>)
    fun removeByIdAsync(id: Long, callback: RepositoryCallback<Unit>)
    fun saveAsync(post: Post, callback: RepositoryCallbackUnit)

    interface RepositoryCallback<T> {
        fun onSuccess(result: T)
        fun onError(e: Exception)
    }

    interface RepositoryCallbackUnit {
        fun onSuccess(value: Unit)
        fun onError(e: Exception)
    }
}


