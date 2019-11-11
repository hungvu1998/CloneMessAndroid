package com.example.clonemessandroid.ui.register

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.network.RegisterApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class RegisterViewModel@Inject
constructor(val registerApi: RegisterApi) :ViewModel(){
    private var mNavigator: RegisterNavigator? = null
    var mIsValidUser: MutableLiveData<Boolean> = MutableLiveData()
    var mIsValidPass: MutableLiveData<Boolean> = MutableLiveData()
    var mIsValidRetypePass: MutableLiveData<Boolean> = MutableLiveData()

    fun getNavigator(): RegisterNavigator? {
        return mNavigator
    }

    fun setNavigator(navigator: RegisterNavigator) {
        this.mNavigator = navigator
    }

    fun isValidUser(email:String): Boolean {
        return !TextUtils.isEmpty(email)
        //&& Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassWord(pass:String): Boolean {
        return !TextUtils.isEmpty(pass)
    }
    fun isValidRetypePass(pass:String,passRetype : String):Boolean {
        return  TextUtils.equals(passRetype,pass)
    }

    fun isEmailAndPasswordValid() {
        getNavigator()!!.register()
    }

    fun registerUser(userName:String, pass: String){
        registerApi.register(userName,pass)
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


            })
    }

}