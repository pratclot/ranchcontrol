package com.pratclot.ranchcontrol.service.di

import com.pratclot.ranchcontrol.service.IRestService
import com.pratclot.ranchcontrol.viewmodels.ControlViewModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

const val WS_SERVER_URL = "wss://ranch.pratclot.com/api"
const val REST_SERVER_URL = "https://ranch.pratclot.com/api/"
// const val WS_SERVER_URL = "ws://10.0.2.2:20080/api"
// const val REST_SERVER_URL = "http://10.0.2.2:20080/api/"
// const val WS_SERVER_URL = "ws://10.0.2.2:8080/api"
// const val REST_SERVER_URL = "http://10.0.2.2:8080/api/"

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

    @Provides
    @Singleton
    fun provideRestService(client: OkHttpClient): IRestService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
        return Retrofit.Builder()
            .client(client)
            .baseUrl(REST_SERVER_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create()
    }
}
