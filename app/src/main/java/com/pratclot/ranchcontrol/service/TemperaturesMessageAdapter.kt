package com.pratclot.ranchcontrol.service

import com.pratclot.ranchcontrol.domain.Temperatures
import com.tinder.scarlet.Message
import com.tinder.scarlet.MessageAdapter

class TemperaturesMessageAdapter: MessageAdapter<Temperatures> {
    override fun fromMessage(message: Message): Temperatures {
        TODO("Not yet implemented")
    }

    override fun toMessage(data: Temperatures): Message {
        TODO("Not yet implemented")
    }
}