package com.pratclot.ranchcontrol.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratclot.ranchcontrol.RanchControl
import com.pratclot.ranchcontrol.domain.Temperatures
import com.pratclot.ranchcontrol.service.ISocketService
import com.pratclot.ranchcontrol.service.SocketServiceFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response

const val TAG = "ViewModel"

class ControlViewModel @Inject constructor(
    var jwtInterceptor: JwtInterceptor,
    val application: Application
) : ViewModel() {

    @Inject
    lateinit var socketServiceFactory: SocketServiceFactory
    lateinit var socketService: ISocketService

    private var _temperatures = MutableLiveData<Temperatures>()
    val temperatures: LiveData<Temperatures>
        get() = _temperatures

    val disposable = CompositeDisposable()

    init {
    }

    class JwtInterceptor @Inject constructor() : Interceptor {
        lateinit var client_token: String
        override fun intercept(chain: Interceptor.Chain): Response {
            var request = chain.request().newBuilder()
                .header("Authorization", "Bearer $client_token")
                .build()

            return chain.proceed(request)
        }
    }

    fun createJwtInterceptor(client_token: String) {
        jwtInterceptor.client_token = client_token
    }

    fun createSocketService(lifecycleOwner: LifecycleOwner) {
        if (!this::socketService.isInitialized) {
            (application as RanchControl).appComponent.inject(this)
            socketService = socketServiceFactory.create(lifecycleOwner)
        }
    }

    override fun onCleared() {
        disposable.dispose()
        super.onCleared()
    }

    fun addDisposable() {
        socketService.getTemperatures()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _temperatures.value = it
                    Log.e(TAG, "$it")
                },
                {
                    Log.e(TAG, "$it")
                }
            ).takeIf {
                disposable.size() == 0
            }
            ?.addTo(disposable)
    }
}
