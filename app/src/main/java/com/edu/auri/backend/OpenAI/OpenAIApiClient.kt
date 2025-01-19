package com.edu.auri.backend.OpenAI

import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenAIClient {

    // Insert your actual OpenAI API key here (NOT recommended for production; consider using secure storage)
    private const val OPENAI_API_KEY = ""

    // Interceptor adds the Authorization header required by OpenAI.
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $OPENAI_API_KEY")
            .build()
        chain.proceed(newRequest)
    }

    // Optional: Logging interceptor to help debug requests/responses.
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // Create OkHttp client adding both interceptors.
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    // Build Retrofit instance with the base URL from OpenAIApiService.
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(OpenAIApiService.BASE_URL) // Should be "https://api.openai.com/"
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    // Lazy instantiation of your OpenAIApiService.
    val apiService: OpenAIApiService by lazy {
        retrofit.create(OpenAIApiService::class.java)
    }
}
