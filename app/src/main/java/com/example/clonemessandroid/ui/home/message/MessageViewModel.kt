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
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONException
import java.io.File
import java.lang.RuntimeException
import javax.inject.Inject

class MessageViewModel @Inject constructor(val messageApi: MessageApi,val detailChatApi: DetailChatApi) : ViewModel() {


    var liveDataFriend: MutableLiveData<UserModel> = MutableLiveData()
    var liveDataChatModel: MutableLiveData<ChatModel> = MutableLiveData()
    var liveDataStories: MutableLiveData<Stories> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getProfileFriend(listFriend: List<String>){
        Observable.fromIterable(listFriend)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{name->
                messageApi.getUser(name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({

                        if(it.stories!!.size !=0){
                            getDetailStories(it)
                        }
                        else
                            liveDataFriend.value=it
                    },{
                        Log.e("kiemtraProfileFriend",""+it.message)
                    })
            }

//        Observable.fromIterable(listFriend)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe ({ idFriend ->
//                messageApi.getUserTest(idFriend.trim())
//                    .subscribeOn(Schedulers.io())
//                   .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({it->

//
//
//                    },{it->
//                        Log.d("kiemtraE",""+idFriend)
//                        Log.e("kiemtra",""+it.message)
//                    })
//
//            },{erro->
//                Log.e("kiemtra2",""+erro.message)
//
//            })
    }
    @SuppressLint("CheckResult")
    fun getListChat(idChat :String){

        detailChatApi.getListChatApi(idChat,0)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeBy (
                onNext = {
                    liveDataChatModel.value=it
                },onError = {
                    Log.e("kiemtra","Error getListChat - "+it.message)
                },
                onComplete = {

                }
            )
    }

    @SuppressLint("CheckResult")
    fun getDetailStories(userModel: UserModel){
        userModel.listStories= ArrayList()

        Observable.fromIterable(userModel.stories)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{idStories->
                detailChatApi.getDetailStories(idStories)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({it->
                        userModel.listStories?.add(it)

                        if(userModel.listStories?.size == userModel.stories?.size){
                            liveDataFriend.value=userModel
                        }
                    },{
                        Log.e("kiemtra","Error getDetailStories - "+it.message)
                    })
            }

    }

    var livedataImgUpdaload : MutableLiveData<ResponseBody> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun upLoadImage(file: File){
        val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
        detailChatApi.upImage(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
                livedataImgUpdaload.value=it
            },{it->
                Log.e("kiemtra","Error upLoadImage - "+it.message)

            })
    }

    @SuppressLint("CheckResult")
    fun upStory(uri:String){
        detailChatApi.postDetailStories(uri)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                liveDataStories.value=it
            },{
                Log.e("kiemtra","Error upStory - "+it.message)
            })
    }
}