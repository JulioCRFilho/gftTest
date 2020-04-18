package com.example.desafio_android_julio_cesar.model.api

import com.example.desafio_android_julio_cesar.model.entity.CharactersResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MarvelApi {
    @GET("v1/public/characters")
    fun getCharacters(@Query("ts") ts: String, @Query("apikey") apikey: String, @Query("hash") hash: String): Call<CharactersResponse>

    companion object {
        fun builder(): Retrofit {
            val interceptor = HttpLoggingInterceptor()
            val headInterceptor = Interceptor { chain ->
                val request =
                    chain.request().newBuilder().addHeader("Content-Type", "application/json")

                try {
                    val response = chain.proceed(request.build())

                    when (response.code) {
                        200 -> return@Interceptor response
                        201 -> return@Interceptor response
                        400 -> return@Interceptor response
                        401 -> return@Interceptor response
                        404 -> return@Interceptor response
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
                .addInterceptor(interceptor)
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