package com.example.desafio_android_julio_cesar.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.model.api.MarvelApi
import com.example.desafio_android_julio_cesar.model.entity.Comic
import com.example.desafio_android_julio_cesar.model.entity.ComicsResponse
import com.example.desafio_android_julio_cesar.utils.generateHash
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.random.Random

class HQViewModel : ViewModel() {
    var hqLiveData: MutableLiveData<Comic> = MutableLiveData()
    var mostExpensiveHqPrice: Float? = null
    lateinit var interactor: Interactor.UI

    private val ts = Random(654).nextLong().toString()
    private val apikey = "5259659b9a1ed3019e42c862b91db8e2"
    private val hash = generateHash(ts, apikey)

    fun getHQ(id: Int) {
        MarvelApi.builder().create(MarvelApi::class.java).getComics(id, ts, apikey, hash)
            .enqueue(object :
                Callback<ComicsResponse> {
                override fun onFailure(call: Call<ComicsResponse>, t: Throwable) {
                    t.message?.let { interactor.onError(it) }
                }

                override fun onResponse(
                    call: Call<ComicsResponse>,
                    response: Response<ComicsResponse>
                ) {
                    if (response.isSuccessful) {
                        findMostExpensiveHQ(response.body()?.data?.results)
                    } else {
                        interactor.onError(response.message())
                    }
                }
            })
    }

    private fun findMostExpensiveHQ(results: MutableList<Comic>?) {
        var mostExpensive: Pair<Int?, Float> = Pair(0, 0.00f)

        results?.forEach { comics ->
            comics.prices?.forEach { comicPrice ->
                comicPrice.price?.let { price ->
                    if (mostExpensive.second < price) {
                        mostExpensive = Pair(comics.id, price)
                    }
                }
            }
        }
        mostExpensiveHqPrice = mostExpensive.second

        hqLiveData.value = results?.find { comic ->
            comic.id == mostExpensive.first
        }
    }
}