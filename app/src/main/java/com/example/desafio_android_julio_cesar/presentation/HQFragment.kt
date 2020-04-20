package com.example.desafio_android_julio_cesar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.CustomDialog
import com.example.desafio_android_julio_cesar.utils.setToolbar
import com.example.desafio_android_julio_cesar.viewModel.HQViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_hq.*

class HQFragment : Fragment(), Interactor.UI {
    private val args: HQFragmentArgs by navArgs()
    private lateinit var viewModel: HQViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HQViewModel::class.java)
        viewModel.getHQ(args.idHQ)
        viewModel.interactor = this
    }

    override fun onCreateView(i: LayoutInflater, v: ViewGroup?, s: Bundle?): View? {
        return i.inflate(R.layout.fragment_hq, v, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar("Detalhes da HQ")
        viewModel.hqLiveData.observe(viewLifecycleOwner, Observer { comic ->
            HqName.text = comic?.title
            HqPrice.text = "$ ${viewModel.mostExpensiveHqPrice}"

            comic.description?.let { description ->
                val hqDescription =
                    if (description.isNotEmpty()) comic.description else "HQ without description"

                HqDescription.text = hqDescription
            }
            Picasso.get()
                .load(comic?.thumbnail?.path + "/standard_fantastic." + comic?.thumbnail?.extension)
                .placeholder(R.drawable.image_progress_great)
                .error(R.drawable.ic_error_black_24dp)
                .into(HqPic)
        })
    }

    override fun onError(msg: String) = context?.let { CustomDialog(it, msg).show() }
}
