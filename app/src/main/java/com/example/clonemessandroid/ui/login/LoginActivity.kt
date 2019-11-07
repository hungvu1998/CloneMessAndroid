package com.example.clonemessandroid.ui.login

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.ui.home.HomeActivity
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory

import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.layout_login.*
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(),LoginNavigator {

    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory



    lateinit var viewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_login)

        viewModel= ViewModelProviders.of(this,providerFactory).get(LoginViewModel::class.java)
        viewModel.setNavigator(this)

        Glide.with(this).load(R.drawable.icon_mess).into(imageView2)
        btnLogIn?.setOnClickListener {
            viewModel.isEmailAndPasswordValid()
        }
        subcribeObservers()
    }


    fun showProgressBar(isShowing:Boolean){
        if(isShowing){
            progress_bar?.visibility= View.VISIBLE
        }
        else{
            progress_bar?.visibility= View.GONE
        }
    }
    fun onLoginSucce(uid: String?){
        val intent= Intent(this,HomeActivity::class.java)
        intent.putExtra("uid",uid)
        startActivity(intent)
        finish()
    }
    private fun subcribeObservers(){
        viewModel.mIsValidUser.observe(this, Observer {it->
            if(it==false){
                edtEmail?.error = "invalid email"
            }
            else
                showProgressBar(true)
        })
        viewModel.mIsValidPass.observe(this, Observer {it->
            if(it==false){
                edtPass?.error = "Password empty"
            }
            else
                showProgressBar(true)
        })
    }


    override fun login() {
        if (viewModel.isValidUser(edtEmail?.text.toString())){
            if(viewModel.isValidPassWord(edtPass?.text.toString())){
                viewModel.mIsValidUser.value = true
                viewModel.mIsValidPass.value = true
                viewModel.loginNormal(edtEmail?.text.toString(),edtPass?.text.toString())
            }
            else{
                viewModel.mIsValidPass.value = false
            }
        }
        else{
            viewModel.mIsValidUser.value = false
        }
    }

    override fun succes(boolean: Boolean,uid:String?) {
        showProgressBar(false)
        if(!boolean){
            Toast.makeText(this,"Email or Password not valid", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Login successful", Toast.LENGTH_SHORT).show()
            onLoginSucce(uid)
        }
    }
}
