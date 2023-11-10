package ru.netology.nmedia.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.netology.nmedia.dto.Post

private const val BASE_URL = "http://10.0.2.2:9999/api/slow/"

interface PostsApiService {

    @GET("posts")
    fun getAll(): Call<List<Post>>

    @POST("posts/{id}/likes")
    fun likeByIdAsync(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}/likes")
    fun disLikeByIdAsync(@Path("id") id: Long): Call<Post>

    @DELETE("posts/{id}")
    fun removeByIdAsync(@Path("id") id: Long): Call<Unit>

    @POST("posts")
    fun saveAsync(@Body post: Post): Call<Post>

}

val logger = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

val client = OkHttpClient.Builder().addInterceptor(logger).build()

val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .client(client)
    .addConverterFactory(GsonConverterFactory.create())
    .build()

object PostsApi {
    val retrofitService by lazy {
        retrofit.create(PostsApiService::class.java)
    }
}