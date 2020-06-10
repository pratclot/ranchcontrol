package com.pratclot.ranchcontrol.service.di

import com.pratclot.ranchcontrol.viewmodels.ControlViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

// const val WS_SERVER_URL = "wss://ranch.pratclot.com/"
// const val WS_SERVER_URL = "ws://10.0.2.2:20080/api"
const val WS_SERVER_URL = "ws://10.0.2.2:8080/api"

@Module
class SocketModule {
    @Provides
    @Singleton
    fun provideJwtInterceptor(): ControlViewModel.JwtInterceptor {
        return ControlViewModel.JwtInterceptor()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(jwtInterceptor: ControlViewModel.JwtInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(jwtInterceptor)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideSocketService(okHttpClient: OkHttpClient, application: Application): ISocketService {
//        return SocketServiceFactory.create(okHttpClient, application)
//    }
}
