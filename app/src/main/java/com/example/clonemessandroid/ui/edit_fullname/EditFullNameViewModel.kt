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
    var liveDataResult: MutableLiveData<Int> = MutableLiveData()
    @SuppressLint("CheckResult")
    fun editProfileUser(userName: String,fullName:String?,avatar:String?,email:String?){
       if(fullName!=null){
           liveDataResult.value=0

           editProfileApi.editProfile(userName,fullName,null,null)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({it->
                   if(it.message!!){
                       liveDataResult.value=1
                   }
                   else{
                       liveDataResult.value=2
                   }

               },{it->


               })
       }else if(email!=null){
           liveDataResult.value=0
           editProfileApi.editProfile(userName,null,null,email)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe({it->
                   if(it.message!!){
                       liveDataResult.value=1
                   }
                   else{
                       liveDataResult.value=2
                   }

               },{it->


               })
       }

    }
}