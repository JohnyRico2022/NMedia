package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.repository.*
import java.io.IOException
import kotlin.concurrent.thread


private val emptyPost = Post(
    id = 0,
    author = "",
    content = "",
    published = "",
    likeByMe = false,
    likes = 0,
    share = 0,
    video = ""
)

class PostViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository = PostRepositoryImpl()
    private val _data = MutableLiveData(FeedModelState())
    val data: LiveData<FeedModelState> = _data
    val edited = MutableLiveData(emptyPost)
    //  private val _postCreated = SingleLiveEvent<Unit>()
//    val postCreated: LiveData<Unit>
//        get() = _postCreated

    init {
        load()
    }

    fun load() {
        thread {
            _data.postValue(FeedModelState(loading = true))

            try {
                val posts = repository.getAll()
                FeedModelState(posts = posts, empty = posts.isEmpty())
            } catch (e: Exception) {
                FeedModelState(error = true)
            }
                .let(_data::postValue)
        }
    }

    fun save() {
        thread {
            edited.value?.let {
                repository.save(
                    it
                )
                load()
            }
            edited.postValue(emptyPost)
        }
    }
    fun removeById(id: Long) {
        thread {
            val old = _data.value?.posts.orEmpty()
            _data.postValue(
                _data.value?.copy(posts = _data.value?.posts.orEmpty()
                    .filter { it.id != id }
                )
            )
            try {
                repository.removeById(id)
            } catch (e: IOException) {
                _data.postValue(_data.value?.copy(posts = old))
            }
        }
    }


    fun changeContent(content: String) {
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun likeById(id: Long) {
        thread {

        }
    }

    fun shareCounter(id: Long) = repository.shareCounter(id)


    fun edit(post: Post) {
        edited.value = post
    }
}