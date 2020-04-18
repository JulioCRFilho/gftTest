package com.example.desafio_android_julio_cesar.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.utils.BaseActivity

class CharacterDetailsActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)
    }

    companion object {
        fun init(context: Context, char: Character) {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra("char", char)
            context.startActivity(intent)
        }
    }
}
