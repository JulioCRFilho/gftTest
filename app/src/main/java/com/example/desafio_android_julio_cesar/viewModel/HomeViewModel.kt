package com.example.desafio_android_julio_cesar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.model.api.MarvelApi
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.presentation.pagedList.HomeDataSource

class HomeViewModel: ViewModel(), Interactor.Router {
    var interactor: Interactor.View ? = null
    val charactersLiveData: LiveData<PagedList<Character>>
    private val homeDataSource = HomeDataSource.Companion.DataSourceFactory(MarvelApi.Companion) {msg ->
        interactor?.onError(msg)
    }

    private val pagedListConfig = PagedList.Config.Builder()
        .setPageSize(20)
        .setInitialLoadSizeHint(30)
        .setPrefetchDistance(10)
        .setEnablePlaceholders(false)
        .build()

    init {
        charactersLiveData = LivePagedListBuilder(homeDataSource, pagedListConfig).build()
    }
}