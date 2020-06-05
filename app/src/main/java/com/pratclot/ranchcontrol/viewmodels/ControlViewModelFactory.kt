package com.pratclot.ranchcontrol.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.pratclot.ranchcontrol.service.ISocketService
import javax.inject.Inject

class ControlViewModelFactory @Inject constructor(
    val context: Context,
    val socketService: ISocketService
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ControlViewModel(context, socketService) as T
    }
}
