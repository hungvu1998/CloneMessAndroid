package com.example.clonemessandroid.ui.login

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import javax.inject.Inject

class LoginViewModel  @Inject
constructor(

): ViewModel() {
    @Inject
    lateinit var database : DatabaseReference
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
        val user = database.child("taikhoan").child(userName.trim())
        user.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
               Log.d("kiemtra",""+p0)
            }

            override fun onDataChange(p0: DataSnapshot) {

                if(p0.value==null){
                    getNavigator()!!.succes(false,null)
                }
                else{
                    if(pass==p0.child("pass").value){
                        getNavigator()!!.succes(true,p0.child("uid").value.toString())
                    }
                    else{
                        getNavigator()!!.succes(false,null)
                    }
                }
            }
        })

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