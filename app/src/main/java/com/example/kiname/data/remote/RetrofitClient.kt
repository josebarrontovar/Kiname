package com.example.kiname.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    companion object {
        private const val BASE_URL = "https://script.google.com/macros/s/AKfycbz-R5oxEGHLJxmEKpkLVmH3FWA_gUwIpkZrL42yxDH8bzi3HgTBVBQBcN47vELNpgtGhg/"
        val instance: RetrofitService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(RetrofitService::class.java)
        }

    }
}