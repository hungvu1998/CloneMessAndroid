package com.example.clonemessandroid.di.register

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.register.RegisterViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RegisterViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(RegisterViewModel::class)
    abstract fun bindAuthViewModel(loginViewModel: RegisterViewModel): ViewModel
}