package com.example.clonemessandroid.ui.login

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.RequestManager
import com.example.clonemessandroid.network.LoginApi
import com.example.clonemessandroid.session_manager.SessionManager
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
//import com.google.firebase.database.*
import javax.inject.Inject

class LoginViewModel  @Inject
constructor(val loginApi: LoginApi,var sessionManager: SessionManager): ViewModel() {
    @Inject
    lateinit var someThing :String
    //lateinit var sharedPreferences : SharedPreferences
//    @Inject
//    lateinit var logo:Drawable
//    @Inject
//    lateinit var requestManager: RequestManager

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
    @SuppressLint("CheckResult")
    fun loginNormal(userName: String, pass: String) {
        loginApi.login(userName,pass)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it->
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


     val lengthGreaterThanSix = ObservableTransformer<String, String> { observable ->
         observable.flatMap {
             Observable.just(it).map { it.trim() } // - abcdefg - |
                 .filter { it.length > 6 }
                 .singleOrError()
                 .onErrorResumeNext {
                     if (it is NoSuchElementException) {
                         Single.error(Exception("Length should be greater than 6"))
                     } else {
                         Single.error(it)
                     }
                 }
                 .toObservable()
         }
    }

     val verifyEmailPattern = ObservableTransformer<String, String> { observable ->
         observable.flatMap {
             Observable.just(it)
                 .map { it.trim() }
                 .filter {
                     Patterns.EMAIL_ADDRESS.matcher(it).matches()
                 }
                 .singleOrError()
                 .onErrorResumeNext {
                     if (it is NoSuchElementException) {
                         Single.error(Exception("Email not valid"))
                     } else {
                         Single.error(it)
                     }
                 }
                 .toObservable()
         }
    }
    private inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit): ObservableTransformer<String, String> = ObservableTransformer { observable ->
        observable.retryWhen { errors ->
            errors.flatMap {
                onError(it)
                Observable.just("")
            }
        }
    }
}

