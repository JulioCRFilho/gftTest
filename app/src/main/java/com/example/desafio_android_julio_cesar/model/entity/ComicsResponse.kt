package com.example.desafio_android_julio_cesar.model.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ComicsResponse (
    @SerializedName("data")
    var data: ComicData? = null
) : Serializable

data class ComicData (
    @SerializedName("results")
    var results: MutableList<Comic>? = null
) : Serializable

data class Comic (
    @SerializedName("id")
    var id: Int? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("description")
    var description: String? = null,
    @SerializedName("prices")
    var prices: List<ComicPrice>? = null,
    @SerializedName("thumbnail")
    var thumbnail: CharImage? = null
) : Serializable

data class ComicPrice (
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("price")
    var price: Float? = null
) : Serializable