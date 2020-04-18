package com.example.desafio_android_julio_cesar.presentation.adapter

import android.content.Context
import android.net.Uri
import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.desafio_android_julio_cesar.R
import com.example.desafio_android_julio_cesar.model.entity.Character
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.adapter_character.view.*

class HomeAdapter(private val context: Context, private val charList: List<Character?>, private val onClick: (Character) -> Unit) :
    RecyclerView.Adapter<HomeAdapter.ViewHolder>() {
    override fun getItemCount(): Int = charList.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_character, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(charList[position], onClick)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var charPic: ImageView = itemView.charPic
        var charName: TextView = itemView.charName
        var char: LinearLayout = itemView.charLayout

        fun bind(Char: Character?, onClick: (Character) -> Unit) {
            Char?.let {character ->
                charName.text = character.name
                char.setOnClickListener { onClick(character) }

                character.thumbnail.let { img ->
                    Picasso.get()
                        .load(img?.path + "/standard_xlarge." + img?.extension)
                        .resize(200, 200)
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_error_black_24dp)
                        .into(charPic)
                }
            }
        }
    }
}