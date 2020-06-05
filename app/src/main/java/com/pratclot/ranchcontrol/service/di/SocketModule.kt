package com.pratclot.ranchcontrol.service.di

import android.app.Application
import com.pratclot.ranchcontrol.domain.Temperatures
import com.pratclot.ranchcontrol.domain.TemperaturesJsonAdapter
import com.pratclot.ranchcontrol.service.ISocketService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.retry.BackoffStrategy
import com.tinder.scarlet.retry.LinearBackoffStrategy
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val WS_SERVER_URL = "wss://ranch.pratclot.com/"

@Module
class SocketModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideSocketService(okHttpClient: OkHttpClient, application: Application): ISocketService {
        val lifecycle = AndroidLifecycle.ofApplicationForeground(application)
        val backoffStrategy = LinearBackoffStrategy(2_000)
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(WS_SERVER_URL))
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(moshi))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .lifecycle(lifecycle)
            .backoffStrategy(backoffStrategy)
            .build()
            .create(ISocketService::class.java)
    }
}