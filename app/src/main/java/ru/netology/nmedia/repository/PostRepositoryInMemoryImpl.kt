package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {

    private var nextId =1L
    private var posts = listOf(
        Post(
            nextId++,
            "Нетология. Университет интернет-профессий будущего",
            "Тестовый пост №1",
            "21 мая в 18:36",
            likes = 5,
            share = 19,
            likeByMe = false,
            video = ""
        ),
        Post(
            nextId++,
            "Нетология. Университет интернет-профессий будущего",
            "Тестовый пост №2",
            "22 мая в 18:36",
            likes = 999,
            share = 99_999,
            likeByMe = false,
            video = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            nextId++,
            "Нетология. Университет интернет-профессий будущего",
            "Тестовый пост №3",
            "23 мая в 18:36",
            likes = 999,
            share = 99_999,
            likeByMe = false,
            video = ""
        ),
        Post(
            nextId++,
            "Нетология. Университет интернет-профессий будущего",
            "Тестовый пост №4",
            "24 мая в 18:36",
            likes = 999,
            share = 99_999,
            likeByMe = false,
            video = ""
        ),
        Post(
            nextId++,
            "Нетология. Университет интернет-профессий будущего",
            "Тестовый пост №5",
            "25 мая в 18:36",
            likes = 999,
            share = 99_999,
            likeByMe = false,
            video = ""
        )
    )

    private val data = MutableLiveData(posts)
    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                if (it.likeByMe)
                    it.copy(likeByMe = !it.likeByMe, likes = it.likes - 1)
                else it.copy(likeByMe = !it.likeByMe, likes = it.likes + 1)
            }
        }
        data.value = posts
    }

    override fun shareCounter(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(share = it.share + 1)
        }
        data.value = posts
    }
    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }
    override fun save(post: Post) {
        posts = if (post.id == 0L){
            listOf(post.copy(id = nextId++, author = "Me", published = "Now")) + posts
        } else {
            posts.map {if (it.id != post.id) it else it.copy(content = post.content)     }
        }
        data.value = posts
    }
}