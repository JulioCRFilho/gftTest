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
import java.lang.Thread.sleep

open class CustomDialog(context: Context, private val error: String) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_loading)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        failMsg.text = error

        Thread {
            sleep(4500)
            this.dismiss()
        }.start()
    }
}