package com.example.desafio_android_julio_cesar.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.desafio_android_julio_cesar.R
import kotlinx.android.synthetic.main.dialog_loading.*

class CustomDialog(context: Context, private val liveData: MutableLiveData<Pair<Int, String?>>, private val owner: LifecycleOwner) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        liveData.observe(owner, Observer {
            viewFlipper.displayedChild = it.first

            this.setCancelable(it.first != 0)

            it.second?.let { msg ->
                failMsg.text = msg
            }

            if(it.first == 1) {
                this.dismiss()
            }
        })
    }
}