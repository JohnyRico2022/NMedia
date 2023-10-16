package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val share: Int,
    val likeByMe: Boolean,
    val video: String
) {
    fun toDto() = Post(id, author, content, published, likes, share, likeByMe, video)

    companion object {
        fun fromDto(post: Post) =
            PostEntity(post.id, post.author, post.content, post.published, post.likes, post.share, post.likeByMe, post.video)
    }
}