package com.example.desafio_android_julio_cesar.utils

import androidx.fragment.app.Fragment

fun Fragment.setToolbar(title: String, backEnabled: Boolean = true) {
    val mToolbar = (activity as BaseActivity).supportActionBar
    mToolbar?.title = title
    mToolbar?.setDisplayHomeAsUpEnabled(backEnabled)
}