package com.example.clonemessandroid.ui.camera

import android.content.Context
import android.content.res.Configuration
import android.hardware.Camera
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.io.IOException

class ShowCamera(context: Context, internal var camera: Camera) : SurfaceView(context),
    SurfaceHolder.Callback {
    internal var holder: SurfaceHolder

    init {
        holder = getHolder()
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        val parameters = camera.parameters
        if (this.resources.configuration.orientation != Configuration.ORIENTATION_LANDSCAPE) {
            parameters.set("orientation", "portrait")
            camera.setDisplayOrientation(90)
            parameters.setRotation(90)
        } else {
            parameters.set("orientation", "landscape")
            camera.setDisplayOrientation(0)
            parameters.setRotation(0)
        }
        camera.parameters = parameters
        try {
            camera.setPreviewDisplay(holder)
            camera.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }
}
