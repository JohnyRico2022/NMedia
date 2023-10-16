package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int,
    val share: Int,
    val likeByMe: Boolean,
    val video: String
)