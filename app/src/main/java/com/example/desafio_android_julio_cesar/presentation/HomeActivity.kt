package com.example.desafio_android_julio_cesar.presentation

import android.os.Bundle
import android.util.Log.d
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.presentation.pagedList.HomePagedAdapter
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.CustomDialog
import com.example.desafio_android_julio_cesar.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.include_toolbar.toolbar

class HomeActivity : BaseActivity(), Interactor.View {
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setToolbar(toolbar, "Lista de heróis", false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.interactor = this
        viewModel.charactersLiveData.observe(this, Observer { list ->
            val homeAdapter = HomePagedAdapter { char ->
                viewModel.characterDetails(this, char)
            }
            with(recyclerView) {
                layoutManager = LinearLayoutManager(this@HomeActivity, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                adapter = homeAdapter
            }
            homeAdapter.submitList(list)
        })
    }

    override fun onError(msg: String) = CustomDialog(this, msg).show()
}
