package com.example.clonemessandroid.ui.profile

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.network.DetailChatApi
import com.example.clonemessandroid.network.EditProfileApi
import com.example.clonemessandroid.network.MessageApi
import com.example.clonemessandroid.session_manager.SessionManager
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import java.io.File
import javax.inject.Inject

class ProfileViewModel  @Inject
constructor(val messageApi: MessageApi,var sessionManager: SessionManager) : ViewModel(){
    var liveDataFriend: MutableLiveData<UserModel> = MutableLiveData()
    var livedataImgUpdaload :MutableLiveData<ResponseBody> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getProfileFriend(userName: String){
                messageApi.getUser(userName)
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

    fun logOutUser(context: Context){
        sessionManager.logoutUser(context)
    }
    @SuppressLint("CheckResult")
    fun upLoadImage(file: File){
        val requestFile = RequestBody.create(MediaType.parse("image/png"), file)
        val body = MultipartBody.Part.createFormData("files", file.name, requestFile)
        messageApi.upImage(body)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
                livedataImgUpdaload.value=it

            },{it->
                Log.d("kiemtraLoi",""+it.message)
            })
    }


    var liveDataResult: MutableLiveData<Boolean> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun editImage(userName: String,img:String){
        liveDataResult.value=false
        messageApi.editProfile(userName,null,img)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({it->
                if(it.message!!){
                    liveDataResult.value=true
                }
                else{

                }

            },{it->

            })
    }
}