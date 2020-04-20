package com.example.desafio_android_julio_cesar

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.example.desafio_android_julio_cesar.presentation.CharacterDetailsFragment
import com.example.desafio_android_julio_cesar.presentation.CharacterDetailsFragmentDirections
import com.example.desafio_android_julio_cesar.presentation.HomeFragment
import com.example.desafio_android_julio_cesar.presentation.HomeFragmentDirections

interface Interactor {
    interface UI {
        fun onError(msg: String) : Unit?
    }

    interface Router {
        fun navCharDetails(navController: NavController, char: Character) {
            val action = HomeFragmentDirections.navToCharDetails(char)
            navController.navigate(action)
        }

        fun navHQ(navController: NavController, id: Int) {
            val action = CharacterDetailsFragmentDirections.navToHQ(id)
            navController.navigate(action)
        }
    }
}