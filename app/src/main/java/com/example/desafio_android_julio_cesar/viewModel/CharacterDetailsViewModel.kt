package com.example.desafio_android_julio_cesar.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.desafio_android_julio_cesar.Interactor

class CharacterDetailsViewModel: ViewModel(), Interactor.Router {
    fun requestHQ(context: Context, id: Int?) {
        hqDetails(context, id)
    }
}