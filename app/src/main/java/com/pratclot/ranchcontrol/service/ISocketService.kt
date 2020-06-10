package com.pratclot.ranchcontrol.service

import com.pratclot.ranchcontrol.domain.Temperatures
import com.tinder.scarlet.ws.Receive
import io.reactivex.Flowable
import io.reactivex.Observable

interface ISocketService {
    @Receive
    fun getTemperatures(): Flowable<Temperatures>

    @Receive
    fun getTemperaturesObservable(): Observable<Temperatures>
}
