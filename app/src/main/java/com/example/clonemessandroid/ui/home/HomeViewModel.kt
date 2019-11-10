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

    }
}