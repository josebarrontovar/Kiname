package com.example.kiname.data.remote

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface RetrofitService {
    @POST("exec")
    suspend fun addAllData(@Body data: Map<String, String>): Response<PostResponse>
}