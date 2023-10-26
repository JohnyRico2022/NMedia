package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import ru.netology.nmedia.dto.Post
import java.util.concurrent.TimeUnit


class PostRepositoryImpl : PostRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val postsType = object : TypeToken<List<Post>>() {}.type

    private companion object {
        const val BASE_URL = "http://10.0.2.2:9999/api/slow/"
        val jsonType = "application/json".toMediaType()
    }

    override fun getAll(): List<Post> {
        val request = Request.Builder()
            .url("${BASE_URL}posts")
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        val responseString = response.body?.string() ?: error("Body is null")
        return gson.fromJson(responseString, postsType)
    }

    override fun likeById(id: Long) {
        val request = Request.Builder()
            .url("${BASE_URL}posts/${id}/likes")
            .post(gson.toJson(id).toRequestBody(jsonType))
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun disLikeById(id: Long) {
        val request = Request.Builder()
            .url("${BASE_URL}posts/${id}/likes")
            .delete(gson.toJson(id).toRequestBody(jsonType))
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun removeById(id: Long) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}posts/${id}")
            .delete()
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun shareCounter(id: Long) {
        val request: Request = Request.Builder()
            .post(gson.toJson(id).toRequestBody(jsonType))
            .url("${BASE_URL}posts/${id}/share")
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

    override fun save(post: Post) {
        val request = Request.Builder()
            .url("${BASE_URL}posts")
            .post(gson.toJson(post).toRequestBody(jsonType))
            .build()

        client.newCall(request)
            .execute()
            .close()
    }

}