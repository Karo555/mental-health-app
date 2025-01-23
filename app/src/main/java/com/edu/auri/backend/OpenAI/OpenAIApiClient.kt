package com.edu.auri.backend.OpenAI

import com.edu.auri.BuildConfig  // Make sure this matches your app's package name where BuildConfig is generated
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object that configures and provides an instance of [OpenAIApiService] for making network requests to OpenAI.
 *
 * This object sets up Retrofit with the necessary interceptors for authentication and logging.
 */
object OpenAIClient {

    /**
     * The API key for authenticating requests with OpenAI, retrieved from BuildConfig.
     */
    private const val openAiApiKey = BuildConfig.OPENAI_API_KEY

    /**
     * Interceptor that adds the Authorization header required by the OpenAI API.
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $openAiApiKey")
            .build()
        chain.proceed(newRequest)
    }

    /**
     * Logging interceptor for HTTP requests and responses.
     * In production, consider lowering the log level.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * OkHttp client configured with both the authentication and logging interceptors.
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Retrofit instance configured with the OpenAIApiService base URL, the OkHttp client,
     * and a Gson converter for JSON serialization and deserialization.
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(OpenAIApiService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    /**
     * Lazily instantiated service for accessing OpenAI API endpoints.
     */
    val apiService: OpenAIApiService by lazy {
        retrofit.create(OpenAIApiService::class.java)
    }
}