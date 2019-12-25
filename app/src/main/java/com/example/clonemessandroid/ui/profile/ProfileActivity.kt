package com.example.clonemessandroid.ui.profile

import android.Manifest
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import com.squareup.picasso.Picasso
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_profile.*
import kotlinx.android.synthetic.main.progessbar_profile.*
import javax.inject.Inject
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.emitter.Emitter
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.clonemessandroid.ui.edit_fullname.EditFullNameActivity
import com.example.clonemessandroid.ui.login.LoginActivity
import com.example.clonemessandroid.util.ImageFilePath
import java.io.File


class ProfileActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory


    lateinit var userName: String
    private val startTyping = false

    lateinit var viewModel: ProfileViewModel

    val REQUEST_PERMISSION_GALLERY=6

    val REQUEST_EDIT_FULLNAME=1
    val PICK_IMAGE = 2
    var userModel: UserModel?=null

    val FIRST_START = "FIRST_START"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val firstStart = getPreferences(Context.MODE_PRIVATE).getBoolean(FIRST_START, true)
//        if(firstStart){
//            getPreferences(Context.MODE_PRIVATE).getBoolean(FIRST_START, true)
//        }
        setContentView(R.layout.layout_profile)
        userName= intent.getStringExtra("userName")
        viewModel =ViewModelProviders.of(this,providerFactory).get(ProfileViewModel::class.java)
        viewModel.getProfileFriend(userName)
        viewModel.liveDataFriend.observe(this, Observer {it->
                userModel=it
                txtUserNameEdit?.text=it.username
                txtFullName?.text=it.fullname
                txtFullNameEdit?.text=it.fullname
                txtEmail?.text=it.email
                Picasso.get().load(it.avatar).into(img_profile)

            })
        img_back?.setOnClickListener {
            finish()
        }
        viewModel.livedataImgUpdaload.observe(this, Observer { it->
            viewModel.editImage(userName,it.string())
        })
        viewModel.liveDataResult.observe(this, Observer { it->
            if(it){
                progress_barImage.visibility=View.GONE
                viewModel.getProfileFriend(userName)
            }
            else{
                Picasso.get().load(R.drawable.white_background).into(img_profile)
                progress_barImage.visibility=View.VISIBLE

            }
        })
        imgCamera?.setOnClickListener {
            permissionPicture()
        }
        layout_logout?.setOnClickListener {
            viewModel.logOutUser(this)
//            val intent= Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
        }
        layout_userName?.setOnClickListener {
            if(txtFullName?.text.toString().trim()!="" && userModel!= null){
                val intent = Intent(this,EditFullNameActivity::class.java)
                intent.putExtra("typeEdit",1)
                intent.putExtra("userModel",userModel)
                startActivityForResult(intent,REQUEST_EDIT_FULLNAME)
            }

        }
        layout_email?.setOnClickListener {
            if(userModel!= null){
                val intent = Intent(this,EditFullNameActivity::class.java)
                intent.putExtra("typeEdit",2)
                intent.putExtra("userModel",userModel)
                startActivityForResult(intent,REQUEST_EDIT_FULLNAME)
            }
        }
        subcribeObservers()
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


    private fun subcribeObservers(){
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_EDIT_FULLNAME){
            if (resultCode == Activity.RESULT_OK){
                viewModel.getProfileFriend(userName)
            }
        }else  if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK && data!=null) {
            val path = ImageFilePath.getPathFromUri(this,data.data!!)
            val file = File(path)
            viewModel.upLoadImage(file)
        }
    }


}