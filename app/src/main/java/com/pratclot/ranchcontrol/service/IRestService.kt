package com.pratclot.ranchcontrol.service

import io.reactivex.Observable
import retrofit2.http.GET

interface IRestService {
    @GET("/api/turnHeaterOn")
    fun turnHeaterOn(): Observable<String>

    @GET("/api/turnHeaterOff")
    fun turnHeaterOff(): Observable<String>
}
