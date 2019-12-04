package com.example.clonemessandroid.di.camera

import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.di.ViewModelKey
import com.example.clonemessandroid.ui.camera.CameraViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap


@Module
abstract class CameraViewModelsModule {
    @Binds
    @IntoMap
    @ViewModelKey(CameraViewModel::class)
    abstract fun bindCameraViewModel(cameraViewModel: CameraViewModel): ViewModel
}