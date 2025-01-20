package com.edu.auri.backend.OpenAI

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
 * Ensure that you insert your actual OpenAI API key in [OPENAI_API_KEY] or use a secure storage mechanism.
 */
object OpenAIClient {

    /**
     * The API key for authenticating requests with OpenAI.
     *
     * **Important:** Replace the empty string with your actual API key or implement a secure retrieval mechanism.
     */
    private const val OPENAI_API_KEY = ""

    /**
     * Interceptor that adds the Authorization header required by the OpenAI API.
     *
     * This interceptor intercepts outgoing HTTP requests and appends the header:
     * `"Authorization: Bearer <OPENAI_API_KEY>"` to each request.
     */
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val newRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $OPENAI_API_KEY")
            .build()
        chain.proceed(newRequest)
    }

    /**
     * Logging interceptor for HTTP requests and responses.
     *
     * This interceptor logs detailed information (at the BODY level) which is useful for debugging.
     * In production, consider reducing the log level to avoid exposing sensitive information.
     */
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    /**
     * OkHttp client configured with both the authentication and logging interceptors.
     *
     * This client is used by Retrofit to handle network requests.
     */
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .addInterceptor(loggingInterceptor)
        .build()

    /**
     * Retrofit instance configured with the base URL from [OpenAIApiService.BASE_URL], the OkHttp client,
     * and a Gson converter for JSON serialization and deserialization.
     *
     * This instance is lazily initialized.
     */
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(OpenAIApiService.BASE_URL) // Expected to be "https://api.openai.com/"
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }

    /**
     * Lazily instantiated service for accessing OpenAI API endpoints.
     *
     * The service interface [OpenAIApiService] is implemented by Retrofit.
     */
    val apiService: OpenAIApiService by lazy {
        retrofit.create(OpenAIApiService::class.java)
    }
}

