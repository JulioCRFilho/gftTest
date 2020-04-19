package com.example.desafio_android_julio_cesar.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.CustomDialog
import com.example.desafio_android_julio_cesar.viewModel.HQViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_hq.*
import kotlinx.android.synthetic.main.include_toolbar.toolbar

class HQActivity : BaseActivity(), Interactor.View {
    lateinit var viewModel: HQViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hq)
        setToolbar(toolbar, "Detalhes da HQ")
        viewModel = ViewModelProvider(this).get(HQViewModel::class.java)
        val id = intent.getIntExtra("id", 0)
        viewModel.getHQ(id)
        viewModel.interactor = this

        viewModel.hqLiveData.observe(this, Observer { comic ->
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

    companion object {
        fun launch(context: Context, id: Int?) {
            val intent = Intent(context, HQActivity::class.java)
            intent.putExtra("id", id)
            context.startActivity(intent)
        }
    }

    override fun onError(msg: String) {
        CustomDialog(this, msg).show()
    }
}
