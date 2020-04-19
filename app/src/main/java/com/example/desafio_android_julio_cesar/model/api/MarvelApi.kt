package com.example.desafio_android_julio_cesar.model.api

import com.example.desafio_android_julio_cesar.model.entity.CharactersResponse
import com.example.desafio_android_julio_cesar.model.entity.ComicsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit
import kotlin.random.Random

interface MarvelApi {
    @GET("v1/public/characters")
    fun getCharacters(
        @Query("offset") offset: Int? = 0,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Call<CharactersResponse>

    @GET("v1/public/characters/{characterId}/comics")
    fun getComics(
        @Path("characterId") characterId: Int,
        @Query("ts") ts: String,
        @Query("apikey") apikey: String,
        @Query("hash") hash: String
    ): Call<ComicsResponse>

    companion object {
        fun builder(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            val headInterceptor = Interceptor { chain ->
                val request =
                    chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .build()

                try {
                    val response = chain.proceed(request)

                    when (response.code) {
                        200 -> return@Interceptor response
                        201 -> return@Interceptor response
                        400 -> return@Interceptor response
                        401 -> return@Interceptor response
                        404 -> return@Interceptor response
                        409 -> return@Interceptor response
                        500 -> return@Interceptor response
                        else -> return@Interceptor response
                    }
                } catch (t: Throwable) {
                    throw t
                }
            }

            val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .addNetworkInterceptor(interceptor)
                .addInterceptor(headInterceptor)
                .build()

            return Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}