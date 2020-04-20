package com.example.desafio_android_julio_cesar.utils

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.desafio_android_julio_cesar.R
import kotlinx.android.synthetic.main.include_toolbar.*

open class BaseActivity: AppCompatActivity() {
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        navController = findNavController(R.id.nav_host_fragment_container)
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp() = navController.navigateUp()
}