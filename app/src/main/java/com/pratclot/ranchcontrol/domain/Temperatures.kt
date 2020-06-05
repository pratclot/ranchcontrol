package com.pratclot.ranchcontrol.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temperatures(
//    @Json(name = "tempHeater") val tempHeater: String,
//    @Json (name = "tempCauldron") val tempCauldron: String
    val tempHeater: String,
    val tempCauldron: String
)