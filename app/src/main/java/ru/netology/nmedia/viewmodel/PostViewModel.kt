package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.*
import ru.netology.nmedia.util.SingleLiveEvent


private val emptyPost = Post(
    id = 0,
    author = "",
    content = "",
    published = 0,
    likedByMe = false,
    likes = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModelState())
    val data: LiveData<FeedModelState> = _data
    private val edited = MutableLiveData(emptyPost)
    private val _postCreated = SingleLiveEvent<Unit>()

    init {
        load()
    }

    fun load() {
        _data.value = (FeedModelState(loading = true))
        repository.getAllAsync(object : PostRepository.RepositoryCallback<List<Post>> {

            override fun onSuccess(result: List<Post>) {
                _data.value = (FeedModelState(posts = result, empty = result.isEmpty()))
            }

            override fun onError(e: Exception) {
                _data.value = (FeedModelState(error = true))
            }
        })
    }

    fun save() {
        edited.value?.let { newPost ->
            val new = _data.value?.posts.orEmpty()
                .map { if (it.id == newPost.id) newPost else it }
            repository.saveAsync(newPost, object : PostRepository.RepositoryCallbackUnit {
                override fun onSuccess(value: Unit) {
                    _postCreated.value = Unit
                    _data.value = (FeedModelState(posts = new))
                    load()
                }

                override fun onError(e: Exception) {
                    _data.value = FeedModelState(error = true)
                }
            })
        }
        edited.value = emptyPost
    }

    fun removeById(id: Long) {
        val new = _data.value?.posts.orEmpty().filter { it.id != id }
        repository.removeByIdAsync(id, object : PostRepository.RepositoryCallback<Unit> {
            override fun onSuccess(result: Unit) {
                _data.value = FeedModelState(posts = new)
            }

            override fun onError(e: Exception) {
                _data.value = FeedModelState(error = true)
            }
        })
    }

    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        repository.likeByIdAsync(id, object : PostRepository.RepositoryCallback<Post> {
            override fun onSuccess(result: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map {
                            if (it.id == id) {
                                it.copy(
                                    likes = it.likes + 1,
                                    likedByMe = true
                                )
                            } else it
                        }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.value = FeedModelState(error = true)
            }
        })
    }

    fun disLikeById(id: Long) {
        repository.disLikeByIdAsync(id, object : PostRepository.RepositoryCallback<Post> {
            override fun onSuccess(result: Post) {
                _data.postValue(
                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
                        .map {
                            if (it.id == id) {
                                it.copy(
                                    likes = it.likes - 1,
                                    likedByMe = false
                                )
                            } else it
                        }
                    )
                )
            }

            override fun onError(e: Exception) {
                _data.value = FeedModelState(error = true)
            }
        })
    }

    fun shareCounter(id: Long) {
    }

    fun edit(post: Post) {
        edited.value = post
    }
}