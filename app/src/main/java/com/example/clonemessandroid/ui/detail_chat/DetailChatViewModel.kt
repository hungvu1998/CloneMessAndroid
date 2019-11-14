package com.example.clonemessandroid.ui.detail_chat

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.network.DetailChatApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailChatViewModel  @Inject
constructor(val detailChatApi: DetailChatApi) : ViewModel(){
    var liveDataChat: MutableLiveData<ChatDetailModel> = MutableLiveData()
    var liveListChat :MutableLiveData<List<ChatDetailModel>> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getListDetailChat(idChat:String){
        var listChat : List<ChatDetailModel>
        detailChatApi.getListChat(idChat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
              liveListChat.value  =it.messages!!

            },{it->
                Log.d("kiemtra",""+it)

            })

    }

}