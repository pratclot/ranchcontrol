package com.pratclot.ranchcontrol.tools

import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import com.pratclot.ranchcontrol.R
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

@BindingAdapter("setHeaterImage")
fun ImageView.setHeaterImage(temperatures: LiveData<Temperatures>?) {
    temperatures?.value?.let {
        when (it.heaterStatus) {
            "Off" -> setImageResource(android.R.drawable.presence_offline)
            "On" -> setImageResource(android.R.drawable.presence_online)
        }
    }
}

@BindingAdapter("setPumpImage")
fun ImageView.setPumpImage(temperatures: LiveData<Temperatures>?) {
    temperatures?.value?.let {
        when (it.pumpStatus) {
            "Off" -> setImageResource(android.R.drawable.presence_offline)
            "On" -> setImageResource(android.R.drawable.presence_online)
        }
    }
}

@BindingAdapter("setHeatingButtonText")
fun Button.setHeatingButtonText(temperatures: LiveData<Temperatures>?) {
    if (temperatures != null) {
        if (temperatures.value != null) {
            when (temperatures!!.value!!.heaterStatus) {
                "Off" -> setText(R.string.heatingButtonStartText)
                "On" -> setText(R.string.heatingButtonStopText)
            }
            isEnabled = true
            isClickable = true
        } else {
            setText(R.string.heatingButtonDefaultText)
            isEnabled = false
            isClickable = false
        }
    }
}
