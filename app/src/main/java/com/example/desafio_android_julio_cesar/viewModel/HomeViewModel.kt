package com.example.desafio_android_julio_cesar.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.interactor.HomeInteractor
import com.example.desafio_android_julio_cesar.model.api.MarvelApi
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.model.entity.CharactersResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.random.Random

class HomeViewModel : ViewModel(), HomeInteractor.Router {
    lateinit var homeInteractor: HomeInteractor.View
    val charactersLiveData = MutableLiveData<List<Character?>>()
    val requestStatus = MutableLiveData<Pair<Int, String?>>(Pair(0, null))

    fun loadChars(context: Context) {
        homeInteractor.onLoading()

        val apikey = context.getString(R.string.apikey)
        val ts = Random(654).toString()
        val hash = generateHash(ts, apikey, context.getString(R.string.privateKey))

        MarvelApi.builder().create(MarvelApi::class.java).getCharacters(ts, apikey, hash)
            .enqueue(object : Callback<CharactersResponse> {
                override fun onFailure(call: Call<CharactersResponse>, t: Throwable) {
                    t.message?.let { msg ->
                        homeInteractor.onError(msg)
                    }
                }

                override fun onResponse(
                    call: Call<CharactersResponse>,
                    response: Response<CharactersResponse>
                ) {
                    if (response.isSuccessful) {
                        charactersLiveData.value = response.body()?.data?.results
                        homeInteractor.onSuccess()
                    } else {
                        homeInteractor.onError(response.message())
                    }
                }
            })
    }

    private fun generateHash(ts: String, publicKey: String, privateKey: String): String {
        val getBytes: ByteArray = (ts + privateKey + publicKey).toByteArray()
        val generator = MessageDigest.getInstance("MD5")
        return BigInteger(1, generator.digest(getBytes)).toString(16).padStart(32, '0')
    }
}