package com.example.clonemessandroid.ui.edit_fullname

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.network.EditProfileApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EditFullNameViewModel  @Inject
constructor(val editProfileApi: EditProfileApi) : ViewModel(){
    var liveDataResult: MutableLiveData<Boolean> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun editFullName(userName: String,fullName:String){
        liveDataResult.value=false
        editProfileApi.editProfile(userName,fullName,null)
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