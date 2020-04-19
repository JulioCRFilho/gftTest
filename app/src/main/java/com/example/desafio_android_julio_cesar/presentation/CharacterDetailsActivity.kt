package com.example.desafio_android_julio_cesar.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.CustomDialog
import com.example.desafio_android_julio_cesar.viewModel.CharacterDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_character_details.*
import kotlinx.android.synthetic.main.include_toolbar.toolbar

class CharacterDetailsActivity : BaseActivity() {
    lateinit var viewModel: CharacterDetailsViewModel
    lateinit var char: Character

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
        viewModel = ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)
        char = intent.getSerializableExtra("char") as Character
        setToolbar(toolbar, char.name ?: "Detalhes do herói")
        initChar()
    }

    private fun initChar() {
        val charDescription =
            if (char.description?.isNotEmpty()!!) char.description else "Hero without description"

        heroName.text = char.name
        heroDescription.text = charDescription
        heroBtn.setOnClickListener { viewModel.requestHQ(this, char.id) }

        Picasso.get()
            .load(char.thumbnail?.path + "/standard_fantastic." + char.thumbnail?.extension)
            .placeholder(R.drawable.image_progress_great)
            .error(R.drawable.ic_error_black_24dp)
            .into(heroPic)
    }

    companion object {
        fun init(context: Context, char: Character) {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra("char", char)
            context.startActivity(intent)
        }
    }
}
