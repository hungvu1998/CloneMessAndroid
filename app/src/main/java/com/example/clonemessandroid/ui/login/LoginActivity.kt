package com.example.clonemessandroid.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.example.clonemessandroid.R
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.ui.home.HomeActivity
import com.example.clonemessandroid.ui.register.RegisterActivity
import com.example.clonemessandroid.viewmodels.ViewModelProvidersFactory

import dagger.android.support.DaggerAppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
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
        btnCreate?.setOnClickListener {
            val intent=  Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }


        subcribeObservers()



       // test()
    }

    @SuppressLint("CheckResult")
    fun test(){
        val list = listOf("6", "8")
        list.toObservable()
            .map {it->
                Log.d("kiemtra","map1 - "+Thread.currentThread().name)
                return@map it.toInt()
            }
            .subscribeBy(  // named a
                // rguments for lambda Subscribers
                onNext = { Log.d("kiemtra",""+it+" -"+Thread.currentThread().name) },
                onError =  { Log.d("kiemtra","Erro - "+it.message) },
                onComplete = { Log.d("kiemtra","done") }
            )
    }

    fun showProgressBar(isShowing:Boolean){
        if(isShowing){
            progress_bar?.visibility= View.VISIBLE
        }
        else{
            progress_bar?.visibility= View.GONE
        }
    }
    fun onLoginSucce(userModel: UserModel){
        val intent= Intent(this,HomeActivity::class.java)
        intent.putExtra("userModel",userModel)

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


    @SuppressLint("CheckResult")
    override fun login() {
//        Observable.just(edtEmail?.text.toString())
//            .compose(viewModel.lengthGreaterThanSix)
//            .compose(viewModel.verifyEmailPattern)
//            .subscribe({
//                Log.d("onNext",""+it)
//            },{
//                Log.d("onError",""+it.message)
//            },{
//                Log.d("onComplete","onComplete")
//            }
//            )
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

    override fun succes(boolean: Boolean,userModel: UserModel?) {

        showProgressBar(false)
        if(!boolean){
            Toast.makeText(this,"Email or Password not valid", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"Login successful", Toast.LENGTH_SHORT).show()
            onLoginSucce(userModel!!)
        }
    }


}
