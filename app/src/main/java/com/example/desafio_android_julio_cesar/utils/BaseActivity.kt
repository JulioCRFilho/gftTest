package com.example.desafio_android_julio_cesar.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

open class BaseActivity: AppCompatActivity() {
    protected fun setToolbar(toolbar: Toolbar, title: String, backEnabled: Boolean = true) {
        toolbar.title = title
        this.setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(backEnabled)
    }
}