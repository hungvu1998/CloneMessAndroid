package com.example.clonemessandroid.ui.detail_chat

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.ChatDetailModel
import com.example.clonemessandroid.network.DetailChatApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import java.io.File
import javax.inject.Inject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody


class DetailChatViewModel  @Inject
constructor(val detailChatApi: DetailChatApi) : ViewModel() {

    var liveDataChat: MutableLiveData<ChatDetailModel> = MutableLiveData()
    var liveListChat :MutableLiveData<List<ChatDetailModel>> = MutableLiveData()
    var livedataImgUpdaload :MutableLiveData<ResponseBody> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getListDetailChat(idChat:String){
        detailChatApi.getListChat(idChat)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
              liveListChat.value  =it.messages!!
            },{it->
                Log.d("kiemtra",""+it)
            })

    }

    @SuppressLint("CheckResult")
    fun upLoadImage(file:File){
        val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
        detailChatApi.upImage(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
                livedataImgUpdaload.value=it

            },{it->
                Log.d("kiemtraLoi",""+it.message)
            })
    }

}