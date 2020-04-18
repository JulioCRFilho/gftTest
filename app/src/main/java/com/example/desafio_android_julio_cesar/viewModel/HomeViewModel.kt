package com.example.desafio_android_julio_cesar.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.desafio_android_julio_cesar.interactor.HomeInteractor
import com.example.desafio_android_julio_cesar.model.api.MarvelApi
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.presentation.pagedList.HomeDataSource

class HomeViewModel: ViewModel(), HomeInteractor.Router {
    lateinit var homeInteractor: HomeInteractor.View
    val charactersLiveData: LiveData<PagedList<Character>>
    val requestStatus = MutableLiveData<Pair<Int, String?>>(Pair(0, null))

    private val homeDataSource = HomeDataSource.Companion.DataSourceFactory(MarvelApi.Companion)
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