package com.edu.auri.backend.endpoint

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    private const val BASE_URL = "https://api.openai.com/"
    private const val API_KEY = "your-openai-api-key-here" // Replace with your actual key

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain: Interceptor.Chain ->
            val request = chain.request()
                .newBuilder()
                .addHeader("Authorization", "Bearer $API_KEY")
                .build()
            chain.proceed(request)
        }
        .build()

    val openAIApi: OpenAIApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenAIApi::class.java)
    }
}
