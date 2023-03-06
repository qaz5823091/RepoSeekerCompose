package com.example.reposeekercompose

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Query

class RetrofitHelper private constructor() {
    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder().build()

    init {
        retrofit = Retrofit.Builder()
            .baseUrl(Config.baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    companion object {
        private var helper = RetrofitHelper()
        val client: Retrofit
            get() = helper.retrofit
    }
}