package com.example.clonemessandroid.ui.home.message

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageViewModel : ViewModel {
    @Inject
    constructor( )

    var liveDataFriend: MutableLiveData<UserModel> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getProfileFriend(listFriend: ArrayList<String>){
        var array : ArrayList<UserModel> = ArrayList()

        Observable.fromIterable(listFriend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { idFriend ->

            }



    }

}