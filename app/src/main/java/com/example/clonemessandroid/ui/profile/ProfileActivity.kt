package com.example.clonemessandroid.ui.profile

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
import android.content.Intent
import com.github.nkzawa.socketio.client.IO
import com.github.nkzawa.socketio.client.Socket
import com.github.nkzawa.emitter.Emitter
import android.os.Handler
import android.os.Message
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.clonemessandroid.ui.edit_fullname.EditFullNameActivity
import com.example.clonemessandroid.ui.login.LoginActivity


class ProfileActivity : DaggerAppCompatActivity(){

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory


    lateinit var userName: String
    private val startTyping = false

    lateinit var viewModel: ProfileViewModel



    val REQUEST_EDIT_FULLNAME=1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_profile)

        userName= intent.getStringExtra("userName")
        viewModel =ViewModelProviders.of(this,providerFactory).get(ProfileViewModel::class.java)
        viewModel.getProfileFriend(userName)
        viewModel.liveDataFriend.observe(this, Observer {it->
                txtUserNameEdit?.text=it.username
                txtFullName?.text=it.fullname
                txtFullNameEdit?.text=it.fullname
                Picasso.get().load(it.avatar).into(img_profile)

            })
        img_back?.setOnClickListener {
            finish()
        }
        layout_logout?.setOnClickListener {
            viewModel.logOutUser(this)
//            val intent= Intent(this, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
//            startActivity(intent)
        }
        layout_userName?.setOnClickListener {
            if(txtFullName?.text.toString().trim()!=""){
                val intent = Intent(this,EditFullNameActivity::class.java)
                intent.putExtra("fullname",txtFullName.text.toString())
                intent.putExtra("userName",userName)
                startActivityForResult(intent,REQUEST_EDIT_FULLNAME)
            }

        }
        subcribeObservers()
    }





    private fun subcribeObservers(){

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_EDIT_FULLNAME){
            if (resultCode == Activity.RESULT_OK){
                viewModel.getProfileFriend(userName)
            }
        }
    }


}