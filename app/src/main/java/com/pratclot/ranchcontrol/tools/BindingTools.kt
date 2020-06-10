package com.pratclot.ranchcontrol.tools

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.pratclot.ranchcontrol.domain.Temperatures

@BindingAdapter("setTempCauldron")
fun TextView.setTempCauldron(temperatures: LiveData<Temperatures>?) {
    temperatures?.value?.let {
        text = it.tempCauldron
    }
}

@BindingAdapter("setTempHeater")
fun TextView.setTempHeater(temperatures: LiveData<Temperatures>?) {
    temperatures?.value?.let {
        text = it.tempHeater
    }
}
