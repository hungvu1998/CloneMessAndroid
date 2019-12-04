package com.example.clonemessandroid.ui.camera

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory

import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_camera.*
import javax.inject.Inject
import com.example.clonemessandroid.ui.call.CameraPreview
import com.example.clonemessandroid.util.ImageFilePath
import java.io.File
import android.graphics.Bitmap





class CameraActivity : DaggerAppCompatActivity() {



    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory
    lateinit var viewModel:CameraViewModel
     var mCamera:Camera ? =null
    lateinit var mPreview:CameraPreview
    lateinit var mPicture:Camera.PictureCallback
    var cameraFront = false
    val REQUEST_PERMISSION_GALLERY=6
    val PICK_IMAGE = 1
    var bitmap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_camera)
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        viewModel = ViewModelProviders.of(this,providerFactory).get(CameraViewModel::class.java)
        mCamera =  Camera.open()
        mCamera?.setDisplayOrientation(90)
        mPreview =  CameraPreview(this, mCamera)
        cPreview.addView(mPreview)
        mCamera?.startPreview()

        btnGallary.setOnClickListener {
            permissionPicture()
        }

        btnCam.setOnClickListener {
            mCamera?.takePicture(null, null, mPicture)
        }
        btnSwitch.setOnClickListener {
            val camerasNumber = Camera.getNumberOfCameras()
            if (camerasNumber > 1) {
                //release the old camera instance
                //switch camera, from the front and the back and vice versa

                releaseCamera()
                chooseCamera()
            } else {

            }
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && null != data) {
                val path = ImageFilePath.getPathFromUri(this,data.data!!)
                val file = File(path)
                Log.d("kiemtra",""+file)

//                viewModel.upLoadImage(file)
//
//                var chatDetailModel = ChatDetailModel()
//                if (idChat==null){
//                    idChat = ""
//                }
//                chatDetailModel.idChat=idChat
//                chatDetailModel.content=data.data.toString()
//                chatDetailModel.from=from
//                chatDetailModel.to=to
//                chatDetailModel.type=3
//
//                viewModel.liveDataChat.value= chatDetailModel


        }

    }
    fun permissionPicture(){
        val checkPermissionExternalStorage:Int= ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkPermissionExternalStorage!= PackageManager.PERMISSION_GRANTED ){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_PERMISSION_GALLERY)
        }
        else{
            succesPermissionpicture()
        }
    }
    fun succesPermissionpicture(){
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false)
        intent.action = Intent.ACTION_GET_CONTENT
        //intent.action = Intent.ACTION_PICK
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE)
        //startActivityForResult(intent,PICK_IMAGE)
    }
    fun releaseCamera(){
        if (mCamera != null) {
            mCamera!!.stopPreview()
            mCamera!!.setPreviewCallback(null)
            mCamera!!.release()
            mCamera = null
        }
    }
    fun findBackFacingCamera():Int{
        var cameraId = -1
        //Search for the back facing camera
        //get the number of cameras
        val numberOfCameras = Camera.getNumberOfCameras()
        //for every camera check+

        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                cameraId = i
                cameraFront = false
                break

            }

        }
        return cameraId
    }
    fun getPictureCallback(): Camera.PictureCallback{
        Log.d("kiemtra","hello")
        return Camera.PictureCallback { data, camera ->
            bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)

        }
    }
    fun chooseCamera(){
        if (cameraFront) {
            val cameraId = findBackFacingCamera()
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                mCamera = Camera.open(cameraId);
                mCamera?.setDisplayOrientation(90)
                mPicture = getPictureCallback()
                mPreview.refreshCamera(mCamera)
            }
        } else {
            val cameraId = findFrontFacingCamera()
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId)
                mCamera?.setDisplayOrientation(90)
                mPicture = getPictureCallback()
                mPreview.refreshCamera(mCamera)
            }
        }
    }
    fun findFrontFacingCamera():Int{
        var cameraId = -1
        // Search for the front facing camera
        val numberOfCameras = Camera.getNumberOfCameras()
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                cameraId = i
                cameraFront = true
                break
            }
        }
        return cameraId
    }

    override fun onResume() {
        super.onResume()
        if(mCamera == null) {
            mCamera = Camera.open();
            mCamera?.setDisplayOrientation(90)
            mPicture = getPictureCallback()
            mPreview.refreshCamera(mCamera)
            Log.d("nu", "null")
        }else {
            Log.d("nu","no null")
        }
    }

    override fun onPause() {
        super.onPause()
        releaseCamera()
    }

}