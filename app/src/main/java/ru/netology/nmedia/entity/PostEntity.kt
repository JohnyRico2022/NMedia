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
    val likeByMe: Boolean,
    val likes: Int
) {
    fun toDto() = Post(id, author, content, published,likeByMe, likes)

    companion object {
        fun fromDto(post: Post) =
            PostEntity(post.id, post.author, post.content, post.published, post.likeByMe, post.likes)
    }
}