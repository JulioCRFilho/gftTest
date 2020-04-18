package com.example.desafio_android_julio_cesar.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class CharactersResponse (
    @SerializedName("data")
    var data: Container? = null
): Serializable

data class Container (
    @SerializedName("results")
    var results: List<Character?>? = null
): Serializable

data class Character (
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("name")
    var name: String? = null,
    @SerializedName("thumbnail")
    var thumbnail: CharImage? = null
): Serializable

data class CharImage (
    @SerializedName("path")
    var path: String? = null,
    @SerializedName("extension")
    var extension: String? = null
): Serializable