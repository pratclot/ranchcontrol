package com.pratclot.ranchcontrol.service

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.pratclot.ranchcontrol.service.di.WS_SERVER_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.ShutdownReason
import com.tinder.scarlet.websocket.okhttp.OkHttpWebSocket
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.Request

class SocketServiceFactory @Inject constructor(
    val okHttpClient: OkHttpClient,
    val application: Application
) {
    fun create(lifecycleOwner: LifecycleOwner): ISocketService {
//            val lifecycle = AndroidLifecycle.ofApplicationForeground(application)
        val lifecycle = AndroidLifecycle.ofLifecycleOwnerForeground(application, lifecycleOwner)
        val backoffStrategy = LinearBackoffStrategy(1_000)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

//        return Scarlet.Builder()
//            .webSocketFactory(okHttpClient.newWebSocketFactory(WS_SERVER_URL))
//            .lifecycle(lifecycle)
//            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
//            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
// //            .backoffStrategy(backoffStrategy)
//            .build()
//            .create(ISocketService::class.java)
        return Scarlet(
            OkHttpWebSocket(
                okHttpClient,
                OkHttpWebSocket.SimpleRequestFactory(
                    { Request.Builder().url(WS_SERVER_URL).build() },
                    { ShutdownReason.GRACEFUL }
                )),
            Scarlet.Configuration(
                lifecycle = lifecycle,
                messageAdapterFactories = listOf(MoshiMessageAdapter.Factory(moshi)),
                streamAdapterFactories = listOf(RxJava2StreamAdapterFactory())
            )
        ).create()
    }
}
