package com.example.desafio_android_julio_cesar.presentation

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.interactor.HomeInteractor
import com.example.desafio_android_julio_cesar.presentation.adapter.HomeAdapter
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.CustomDialog
import com.example.desafio_android_julio_cesar.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_toolbar.toolbar

class HomeActivity : BaseActivity(), HomeInteractor.View {
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setToolbar(toolbar, "Lista de heróis", false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.homeInteractor = this
        viewModel.loadChars(this)

        viewModel.charactersLiveData.observe(this, Observer { list ->
            with(recyclerView) {
                layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                adapter = HomeAdapter(this@HomeActivity, list) { char ->
                    viewModel.characterDetails(context, char)
                }
            }
        })
    }

    override fun onLoading() {
        CustomDialog(this, viewModel.requestStatus, this).show()
    }

    override fun onSuccess() {
        viewModel.requestStatus.value = Pair(1, null)
    }

    override fun onError(msg: String) {
        viewModel.requestStatus.value = Pair(2, msg)
    }
}
