package com.example.clonemessandroid.ui.camera

import android.hardware.Camera
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class CameraActivity : DaggerAppCompatActivity(){
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var camera:Camera
    lateinit var showCamera: ShowCamera
    lateinit var viewModel:CameraViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_camera)
        viewModel = ViewModelProviders.of(this,providerFactory).get(CameraViewModel::class.java)
        camera = Camera.open()
        showCamera = ShowCamera(this,camera)
    }
}