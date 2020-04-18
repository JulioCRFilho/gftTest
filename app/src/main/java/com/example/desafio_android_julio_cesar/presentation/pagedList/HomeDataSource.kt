package com.example.desafio_android_julio_cesar.presentation.pagedList

import android.util.Log.d
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.desafio_android_julio_cesar.model.api.MarvelApi
import com.example.desafio_android_julio_cesar.model.entity.Character
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random

class HomeDataSource(private val marvelApi: MarvelApi.Companion) :
    PageKeyedDataSource<Int, Character>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Character>
    ) {
        createList(1, callback, null)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        createList(params.key + 1, null, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Character>) {
        createList(params.key - 1, null, callback)
    }

    private fun createList(
        newPage: Int,
        initialCallback: LoadInitialCallback<Int, Character>?,
        callback: LoadCallback<Int, Character>?
    ) {
        val ts = Random(654).nextLong().toString()
        val apikey = "5259659b9a1ed3019e42c862b91db8e2"
        fun generateHash(): String {
            val key = "ffc0525a3a562bc331c6ca316644920a906ca20e"
            val getBytes: ByteArray = (ts + key + apikey).toByteArray()
            val generator = MessageDigest.getInstance("MD5")
            return BigInteger(1, generator.digest(getBytes)).toString(16).padStart(32, '0')
        }

        try {
            val request = marvelApi.builder().create(MarvelApi::class.java)
                .getCharacters(newPage * 20, ts, apikey, generateHash()).execute()

            if (request.isSuccessful) {
                request.body()?.data?.results?.let { list ->
                    initialCallback?.onResult(list, null, 1)
                    callback?.onResult(list, newPage)
                }
            } else {
                d("tatata", request.code().toString())
            }
        } catch (t: Exception) {
            d("tatata", t.message ?: "exception")
            throw t
        }
    }

    companion object {
        class DataSourceFactory(
            private val marvelApi: MarvelApi.Companion
        ) : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> = HomeDataSource(marvelApi)
        }
    }
}