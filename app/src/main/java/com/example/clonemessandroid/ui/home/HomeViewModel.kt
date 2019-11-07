package com.example.clonemessandroid.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.UserModel

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel  @Inject constructor() : ViewModel() {
//    @Inject
//    lateinit var database : DatabaseReference

    var liveDataUserModel:MutableLiveData<UserModel> = MutableLiveData()

     fun getUserInfo(uid:String){
//        database.child("users").child(uid).addListenerForSingleValueEvent(object :ValueEventListener{
//            override fun onCancelled(p0: DatabaseError) {
//               Log.d("kiemtra",""+p0)
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                var userModel:UserModel= p0.getValue(UserModel::class.java)!!
//                userModel.uid=p0.key
//                userModel.isActive= p0.child("isActive").value as Boolean?
//
//
//
//
//                liveDataUserModel.value=userModel
//
//            }
//        })
    }
}