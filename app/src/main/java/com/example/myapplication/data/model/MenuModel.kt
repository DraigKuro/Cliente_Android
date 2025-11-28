package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class MenuModel(
    @SerializedName("_id") override val id: String,
    @SerializedName("nombre") override val nombre: String,
    @SerializedName("descripcion") override val descripcion: String,
    @SerializedName("precio") override val precio: Double,
    @SerializedName("imagen") override val imagen: String
) : ItemModel

