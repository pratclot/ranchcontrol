package com.pratclot.ranchcontrol.domain

import com.squareup.moshi.Json

data class Temperatures(
    @Json(name = "temp_heater") val tempHeater: String,
    @Json(name = "temp_cauldron") val tempCauldron: String
)
