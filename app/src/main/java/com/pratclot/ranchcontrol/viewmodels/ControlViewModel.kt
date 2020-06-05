package com.pratclot.ranchcontrol.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pratclot.ranchcontrol.domain.Temperatures
import com.pratclot.ranchcontrol.service.ISocketService
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

const val TAG = "ViewModel"

class ControlViewModel @Inject constructor(
    private val context: Context,
    val socketService: ISocketService
) : ViewModel() {
    var testVal = 1

    private var _tempCauldron = MutableLiveData<String>()
    val tempCauldron: LiveData<String>
        get() = _tempCauldron

//    val disposable = CompositeDisposable()

    init {
        Log.e(TAG, "initing viewmodel")
//        getTempFlow()
    }

    fun getTempFlow(): Disposable {
        return socketService.getTemperatures()
            .subscribe({
                _tempCauldron.value = it.tempCauldron
                Log.e(TAG, "Logging ${it.tempCauldron}")
            }, {
                Log.e(TAG, it.cause.toString())
            })
    }

    fun getFakeFlow(): Flowable<Temperatures> {
        return Flowable.just(Temperatures("blah", "blah"))
    }
}