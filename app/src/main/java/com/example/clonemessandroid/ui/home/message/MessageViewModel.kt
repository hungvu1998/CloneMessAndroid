package com.example.clonemessandroid.ui.home.message

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.ChatModel
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.network.DetailChatApi
import com.example.clonemessandroid.network.MessageApi

import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageViewModel @Inject constructor(val messageApi: MessageApi,val detailChatApi: DetailChatApi) : ViewModel() {


    var liveDataFriend: MutableLiveData<UserModel> = MutableLiveData()
    var liveDataChatModel: MutableLiveData<ChatModel> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getProfileFriend(listFriend: List<String>){
        var array : ArrayList<UserModel> = ArrayList()
        Observable.fromIterable(listFriend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { idFriend ->
                messageApi.getUserTest(idFriend)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({it->
                        if(it.message!!){

                            liveDataFriend.value=it
                        }
                        else{
                        }
                    },{it->
                    })

            }
    }

    @SuppressLint("CheckResult")
    fun getListChat(idChat :String){
                detailChatApi.getListChat(idChat)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        liveDataChatModel.value=it
                    },{it->
                        Log.d("kiemtra",""+it)

                    })


    }


}