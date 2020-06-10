package com.pratclot.ranchcontrol.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ControlViewModelFactory @Inject constructor(
    var jwtInterceptor: ControlViewModel.JwtInterceptor,
    val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ControlViewModel(jwtInterceptor, application) as T
    }
}
