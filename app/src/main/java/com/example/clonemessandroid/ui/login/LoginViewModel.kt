package com.example.clonemessandroid.ui.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.network.LoginApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
//import com.google.firebase.database.*
import javax.inject.Inject

class LoginViewModel  @Inject
constructor(val loginApi: LoginApi): ViewModel() {
    //@Inject
   // lateinit var database : DatabaseReference
    var mIsValidUser: MutableLiveData<Boolean> = MutableLiveData()
    var mIsValidPass: MutableLiveData<Boolean> = MutableLiveData()

    private var mNavigator: LoginNavigator? = null


    fun getNavigator(): LoginNavigator? {
        return mNavigator
    }

    fun setNavigator(navigator: LoginNavigator) {
        this.mNavigator = navigator
    }
    fun isValidUser(email:String): Boolean {
        return !TextUtils.isEmpty(email)
                //&& Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassWord(pass:String): Boolean {
        return !TextUtils.isEmpty(pass)

    }

    fun loginNormal(userName: String, pass: String) {
        loginApi.login(userName,pass)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
                if(it.message!!){

                    getNavigator()!!.succes(true,it)
                }
                else{
                    getNavigator()!!.succes(false,null)
                }

            },{it->
                Log.d("kiemtra","Erro")
                Log.d("kiemtra",""+it.message)
            })
    }

    fun isEmailAndPasswordValid() {
        getNavigator()!!.login()
    }


}