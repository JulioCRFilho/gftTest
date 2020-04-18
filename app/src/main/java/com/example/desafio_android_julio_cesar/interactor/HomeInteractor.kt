package com.example.desafio_android_julio_cesar.interactor

import android.content.Context
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.presentation.CharacterDetailsActivity

interface HomeInteractor {
    interface View {
        fun onLoading()
        fun onSuccess()
        fun onError(msg: String)
    }

    interface Router {
        fun characterDetails(context: Context, char: Character) {
            CharacterDetailsActivity.init(context, char)
        }
    }
}