package com.example.clonemessandroid.ui.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.network.MessageApi

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class HomeViewModel  @Inject constructor(val messageApi: MessageApi) : ViewModel() {
//    @Inject
//    lateinit var database : DatabaseReference

    var liveDataUserModel:MutableLiveData<UserModel> = MutableLiveData()

     @SuppressLint("CheckResult")
     fun getUserInfo(userName: String){
         messageApi.getUser(userName)
             .subscribeOn(Schedulers.io())
             .observeOn(AndroidSchedulers.mainThread())
             .subscribe({it->
                 if(it.message!!){
                     liveDataUserModel.value=it
                 }
                 else{

                 }

             },{it->


             })

    }
}