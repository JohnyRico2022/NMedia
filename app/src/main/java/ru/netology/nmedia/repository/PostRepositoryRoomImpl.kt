package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post


class PostRepositoryRoomImpl(private val dao: PostDao) : PostRepository {

    override fun getAll(): LiveData<List<Post>> = MutableLiveData()

    override fun likeById(id: Long) {

    }

    override fun shareCounter(id: Long) {

    }

    override fun removeById(id: Long) {

    }

    override fun save(post: Post) {

    }
}