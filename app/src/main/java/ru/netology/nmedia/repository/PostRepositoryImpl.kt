package ru.netology.nmedia.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import ru.netology.nmedia.dto.Post
import java.io.IOException
import java.util.concurrent.TimeUnit


class PostRepositoryImpl : PostRepository {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()
    private val typeToken = object : TypeToken<List<Post>>() {}

    private companion object {
        const val BASE_URL = "http://10.0.2.2:9999/api/slow/"
        val jsonType = "application/json".toMediaType()
    }



    override fun getAllAsync(callback: PostRepository.RepositoryCallback<List<Post>>) {
        val request = Request.Builder()
            .url("${BASE_URL}posts")
            .build()

        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    override fun likeByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Post>) {
        val request = Request.Builder()
            .url("${BASE_URL}posts/${id}/likes")
            .post(gson.toJson(id).toRequestBody(jsonType))
            .build()

        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }

    override fun disLikeByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Post>) {
        val request = Request.Builder()
            .url("${BASE_URL}posts/${id}/likes")
            .delete(gson.toJson(id).toRequestBody(jsonType))
            .build()

        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
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

    override fun removeByIdAsync(id: Long, callback: PostRepository.RepositoryCallback<Post>) {
        val request: Request = Request.Builder()
            .url("${BASE_URL}posts/${id}")
            .delete()
            .build()
        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })

    }

    override fun saveAsync(post: Post, callback: PostRepository.RepositoryCallback<Post>) {
        val request = Request.Builder()
            .url("${BASE_URL}posts")
            .post(gson.toJson(post).toRequestBody(jsonType))
            .build()

        client.newCall(request)
            .enqueue(object : Callback {

                override fun onResponse(call: Call, response: Response) {
                    try {
                        val body = response.body?.string() ?: throw RuntimeException("body is null")
                        callback.onSuccess(gson.fromJson(body, typeToken.type))
                    } catch (e: Exception) {
                        callback.onError(e)
                    }
                }

                override fun onFailure(call: Call, e: IOException) {
                    callback.onError(e)
                }
            })
    }
}