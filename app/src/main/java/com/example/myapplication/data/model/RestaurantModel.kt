package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class RestaurantModel(
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String,
    @SerializedName("logoUrl") val logoUrl: String? = null,
    @SerializedName("socialLinks") val socialLinks: Map<String, SocialLink>? = null,
    @SerializedName("cif") val cif: String? = null
)

data class SocialLink(
    val enabled: Boolean,
    val url: String
)