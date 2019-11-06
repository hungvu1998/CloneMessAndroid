package com.example.clonemessandroid.ui.home.message

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.clonemessandroid.data.model.Stories
import com.example.clonemessandroid.data.model.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MessageViewModel : ViewModel {
    @Inject
    lateinit var database : DatabaseReference

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
                database.child("users").child(idFriend).addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onCancelled(p0: DatabaseError) {

                    }
                    override fun onDataChange(p0: DataSnapshot) {
                        var userModel: UserModel = p0.getValue(UserModel::class.java)!!
                        userModel.uid=p0.key
                        userModel.isActive= p0.child("isActive").value as Boolean?
                        if(userModel.stories !=null){
                            Observable.fromIterable(userModel.stories)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe{idStory ->
                                    database.child("stories").child(idStory).addListenerForSingleValueEvent(object :ValueEventListener{
                                        override fun onCancelled(p1: DatabaseError) {
                                        }

                                        override fun onDataChange(p1: DataSnapshot) {

                                            var stories: Stories = p1.getValue(Stories::class.java)!!
                                            userModel.listStories.add(stories)
                                            if(userModel.stories!!.size == userModel.listStories.size){
                                                userModel.listStories.sortBy { it.timestamp }
                                                liveDataFriend.value = userModel
                                            }

                                        }
                                    })

                                }
                        }
                        else{
                            liveDataFriend.value = userModel
                        }
                    }
                })



            }


//        for (item in listFriend){
//            database.child("users").child(item).addListenerForSingleValueEvent(object :ValueEventListener{
//                override fun onCancelled(p0: DatabaseError) {
//
//                }
//
//                override fun onDataChange(p0: DataSnapshot) {
//                    var userModel: UserModel = p0.getValue(UserModel::class.java)!!
//                    userModel.uid=p0.key
//                    if(userModel.stories !=null){
//                        for(item in userModel.stories!!){
//                            database.child("stories").child(item).addListenerForSingleValueEvent(object :ValueEventListener{
//                                override fun onCancelled(p0: DatabaseError) {
//
//                                }
//
//                                override fun onDataChange(p1: DataSnapshot) {

//                                    userModel.listStories.add(stories)
//                                    array.add(userModel)
//                                    liveDataListFriend.value=array
//                                }
//
//                            })
//                        }
//                        if()
//                    }
//                    else{
//                        Log.d("kiemtra","Null stories"+userModel.uid)
//                        array.add(userModel)
//                        liveDataListFriend.value=array
//                    }
//
//
//
//
//                }
//            })
//        }

    }

}