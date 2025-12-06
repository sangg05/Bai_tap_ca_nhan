package com.example.uthsmarttasks.api

import com.example.uthsmarttasks.models.Task
import com.example.uthsmarttasks.models.TasksResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import com.example.uthsmarttasks.models.TaskDetailResponse


interface ApiService {

    @GET("tasks")
    suspend fun getTasks(): Response<TasksResponse>

    @GET("task/{id}")
    suspend fun getTask(@Path("id") id: String): Response<TaskDetailResponse>

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: String): Response<Unit>
}

object RetrofitClient {

    // Base URL chuẩn, bỏ dấu / thừa
    private const val BASE_URL = "https://amock.io/api/researchUTH/"

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}