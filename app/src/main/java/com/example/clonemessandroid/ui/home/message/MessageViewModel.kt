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
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import org.json.JSONException
import java.lang.RuntimeException
import javax.inject.Inject

class MessageViewModel @Inject constructor(val messageApi: MessageApi,val detailChatApi: DetailChatApi) : ViewModel() {


    var liveDataFriend: MutableLiveData<UserModel> = MutableLiveData()
    var liveDataChatModel: MutableLiveData<ChatModel> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getProfileFriend(listFriend: List<String>){
        Observable.fromIterable(listFriend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ idFriend ->
                messageApi.getUserTest(idFriend)
                    .subscribeOn(Schedulers.io())
//                    .filter {
//                        it.message!=null
//                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({it->
                        Log.d("kiemtra",""+it.stories!!.size)
                        if(it.stories!!.size !=0)
                            getDetailStories(it)
                        else
                            liveDataFriend.value=it


                    },{it->
                        Log.d("kiemtra",""+it.message)
                    })

            },{erro->
                Log.d("kiemtra",""+erro.message)

            })
    }
    @SuppressLint("CheckResult")
    fun getListChat(idChat :String){
        Log.d("kiemtra","fffff")
        detailChatApi.getListChat(idChat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    liveDataChatModel.value=it
                },onError = {
                    Log.d("kiemtra","Error" + it.message)
                },
                onComplete = {

                }
            )
    }

    @SuppressLint("CheckResult")
    fun getDetailStories(userModel: UserModel){
        Observable.fromIterable(userModel.stories)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{idStories->
                detailChatApi.getDetailStories(idStories)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        userModel.listStories?.add(it)
                        if(userModel.listStories?.size == userModel.stories?.size){
                            liveDataFriend.value=userModel
                        }
                    }
            }

    }


}