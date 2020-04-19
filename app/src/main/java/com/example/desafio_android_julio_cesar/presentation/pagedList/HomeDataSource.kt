package com.example.desafio_android_julio_cesar.presentation.pagedList

import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.example.desafio_android_julio_cesar.model.api.MarvelApi
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.model.entity.CharactersResponse
import com.example.desafio_android_julio_cesar.utils.generateHash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep
import kotlin.concurrent.thread
import kotlin.random.Random

class HomeDataSource(
    private val marvelApi: MarvelApi.Companion,
    private val interactor: (String) -> Unit
) :
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
        val hash = generateHash(ts, apikey)

        marvelApi.builder().create(MarvelApi::class.java)
            .getCharacters(newPage * 20, ts, apikey, hash).enqueue(object :
                Callback<CharactersResponse> {
                override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                    t.message?.let { msg ->
                        interactor(msg)
                    }
                    Thread {
                        sleep(5000)
                        createList(newPage, initialCallback, callback)
                    }.start()
                }

                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.data?.results?.let { list ->
                            initialCallback?.onResult(list, null, newPage)
                            callback?.onResult(list, newPage)
                        }
                    } else {
                        interactor(response.message())
                    }
                }
            })
    }

    companion object {
        class DataSourceFactory(
            private val marvelApi: MarvelApi.Companion,
            private val interactor: (String) -> Unit
        ) : DataSource.Factory<Int, Character>() {
            override fun create(): DataSource<Int, Character> =
                HomeDataSource(marvelApi, interactor)
        }
    }
}