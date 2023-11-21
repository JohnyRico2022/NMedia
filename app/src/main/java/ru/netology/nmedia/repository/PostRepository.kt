package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

interface PostRepository {
    val data: LiveData<List<Post>>
    suspend fun getAll()
    suspend fun likeById(id: Long)
    suspend fun disLikeById(id: Long)
    suspend fun removeById(id: Long)
    suspend fun save(post: Post)
}
  //  fun shareCounter(id: Long)

   /* interface RepositoryCallback<T> {
        fun onSuccess(result: T)
        fun onError(e: Exception)
    }

    interface RepositoryCallbackUnit {
        fun onSuccess(value: Unit)
        fun onError(e: Exception)
    }*/



