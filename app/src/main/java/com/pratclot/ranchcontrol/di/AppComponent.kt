package com.pratclot.ranchcontrol.di

import android.app.Application
import android.content.Context
import com.pratclot.ranchcontrol.MainActivity
import com.pratclot.ranchcontrol.fragments.ControlFragment
import com.pratclot.ranchcontrol.fragments.ui.login.LoginFragment
import com.pratclot.ranchcontrol.service.di.SocketModule
import com.pratclot.ranchcontrol.viewmodels.ControlViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AssistedModule::class,
        SocketModule::class
    ]
)
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance context: Context,
            @BindsInstance application: Application
        ): AppComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: ControlFragment)
    fun inject(fragment: LoginFragment)
    fun inject(viewModel: ControlViewModel)
}
