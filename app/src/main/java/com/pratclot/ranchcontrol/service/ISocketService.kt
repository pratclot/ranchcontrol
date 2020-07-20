package com.pratclot.ranchcontrol.service

import com.pratclot.ranchcontrol.domain.Temperatures
import com.tinder.scarlet.ws.Receive
import io.reactivex.Flowable

interface ISocketService {
    @Receive
    fun getTemperatures(): Flowable<Temperatures>
}
