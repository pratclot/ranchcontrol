package com.pratclot.ranchcontrol.domain

import com.squareup.moshi.Json

data class Temperatures(
    @Json(name = "temp_heater") val tempHeater: String,
    @Json(name = "temp_cauldron") val tempCauldron: String,
    @Json(name = "heater_status") val heaterStatus: String,
    @Json(name = "pump_status") val pumpStatus: String
)
