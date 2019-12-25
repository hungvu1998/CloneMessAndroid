package com.example.clonemessandroid.ui.register

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.ui.home.HomeActivity
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory
import dagger.android.support.DaggerAppCompatActivity

import kotlinx.android.synthetic.main.layout_register.*
import kotlinx.android.synthetic.main.layout_register.edtEmail
import kotlinx.android.synthetic.main.layout_register.edtPass
import javax.inject.Inject

class RegisterActivity : DaggerAppCompatActivity(),RegisterNavigator{
    override fun succes(boolean: Boolean, userModel: UserModel?) {
        showProgressBar(false)
        if(!boolean){
            Toast.makeText(this,"User have been exist!", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Register successful", Toast.LENGTH_SHORT).show()
            onLoginSucce(userModel!!)
        }
    }

    fun onLoginSucce(userModel: UserModel){
        val intent= Intent(this, HomeActivity::class.java)
        intent.putExtra("userModel",userModel)
        intent.putExtra("userName",userModel.username)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)

    }
    @Inject
    lateinit var providerFactory: ViewModelProvidersFactory

    lateinit var viewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_register)
        viewModel= ViewModelProviders.of(this,providerFactory).get(RegisterViewModel::class.java)
        viewModel.setNavigator(this)
        btnRegister?.setOnClickListener {
            viewModel.isEmailAndPasswordValid()
        }
        txtHuy?.setOnClickListener{
            finish()
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
    override fun register() {


        if (viewModel.isValidUser(edtEmail?.text.toString())){
            if(viewModel.isValidPassWord(edtPass?.text.toString())){
                if(viewModel.isValidRetypePass(edtPass?.text.toString(),edtPassRetype?.text.toString())){
                    viewModel.mIsValidUser.value = true
                    viewModel.mIsValidPass.value = true
                    viewModel.mIsValidRetypePass.value=true
                    viewModel.registerUser(edtEmail?.text.toString(),edtPass?.text.toString())
                }
                else{
                    viewModel.mIsValidRetypePass.value=false
                }
            }
            else{
                viewModel.mIsValidPass.value = false
            }
        }
        else{
            viewModel.mIsValidUser.value = false
        }
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
        viewModel.mIsValidRetypePass.observe(this, Observer {
            if(it==false){
                edtPassRetype?.error = "Password Retype wrong"
            }
            else
                showProgressBar(true)
        })
    }

}