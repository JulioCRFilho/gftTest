package com.example.desafio_android_julio_cesar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.presentation.pagedList.HomePagedAdapter
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.CustomDialog
import com.example.desafio_android_julio_cesar.utils.setToolbar
import com.example.desafio_android_julio_cesar.viewModel.HomeViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.include_toolbar.*

class HomeFragment : Fragment(), Interactor.UI {
    lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.interactor = this
    }

    override fun onCreateView(i: LayoutInflater, v: ViewGroup?, s: Bundle?): View? {
        return i.inflate(R.layout.fragment_home, v, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar("Lista de heróis", false)
        viewModel.charactersLiveData.observe(viewLifecycleOwner, Observer { list ->
            val homeAdapter = HomePagedAdapter { char ->
                viewModel.navCharDetails(findNavController(), char)
            }
            with(recyclerView) {
                layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
                adapter = homeAdapter
            }
            homeAdapter.submitList(list)
        })
    }

    override fun onError(msg: String) = context?.let { CustomDialog(it, msg).show() }
}
