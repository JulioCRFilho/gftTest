package com.example.desafio_android_julio_cesar.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.desafio_android_julio_cesar.Interactor
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.utils.BaseActivity
import com.example.desafio_android_julio_cesar.utils.setToolbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_character_details.*

class CharacterDetailsFragment : Fragment(), Interactor.Router {
    private val args: CharacterDetailsFragmentArgs by navArgs()
    private var char: Character? = null

    override fun onCreateView(i: LayoutInflater, v: ViewGroup?, s: Bundle?): View? {
        char = args.teste
        return i.inflate(R.layout.fragment_character_details, v, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setToolbar(char?.name ?: "Detalhes do herói")
        initChar()
    }

    private fun initChar() {
        val charDescription =
            if (char?.description?.isNotEmpty()!!) char?.description else "Hero without description"

        heroName.text = char?.name
        heroDescription.text = charDescription
        heroBtn.setOnClickListener { char?.id?.let { id -> navHQ(findNavController(), id) } }

        Picasso.get()
            .load(char?.thumbnail?.path + "/standard_fantastic." + char?.thumbnail?.extension)
            .placeholder(R.drawable.image_progress_great)
            .error(R.drawable.ic_error_black_24dp)
            .into(heroPic)
    }
}
