package com.example.clonemessandroid.ui.profile

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.UserModel
import com.example.clonemessandroid.network.MessageApi
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ProfileViewModel  @Inject
constructor(val messageApi: MessageApi) : ViewModel(){
    var liveDataFriend: MutableLiveData<UserModel> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun getProfileFriend(userName: String){
                messageApi.getUserTest(userName)
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