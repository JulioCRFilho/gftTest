package com.example.desafio_android_julio_cesar

import android.content.Context
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.presentation.CharacterDetailsActivity
import com.example.desafio_android_julio_cesar.presentation.HQActivity

interface Interactor {
    interface View {
        fun onError(msg: String)
    }

    interface Router {
        fun characterDetails(context: Context, char: Character) {
            CharacterDetailsActivity.init(context, char)
        }

        fun hqDetails(context: Context, id: Int?) {
            HQActivity.launch(context, id)
        }
    }
}