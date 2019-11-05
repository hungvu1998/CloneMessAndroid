package com.example.clonemessandroid.ui.login

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel  @Inject
constructor(): ViewModel() {
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
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    fun isValidPassWord(pass:String): Boolean {
        return !TextUtils.isEmpty(pass)
    }

    fun loginNormal(userName: String, pass: String) {
        getNavigator()!!.succes(true)
//        firebaseAuth.signInWithEmailAndPassword(userName, pass).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//
//            } else {
//                getNavigator()!!.succes(false)
//            }
//        }
    }

    fun isEmailAndPasswordValid() {
        getNavigator()!!.login()
    }


}