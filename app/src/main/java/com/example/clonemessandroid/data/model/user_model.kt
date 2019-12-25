package com.example.clonemessandroid.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class UserModel() : Parcelable{
    @SerializedName("username")
    @Expose
    var username:String?=null

    @SerializedName("fullname")
    @Expose
    var fullname:String?=null

    @SerializedName("avatar")
    @Expose
    var avatar:String?=null


    @SerializedName("email")
    @Expose
    var email:String?=null

    @SerializedName("message")
    @Expose
    var message:Boolean?=null

    @SerializedName("friends")
    @Expose
    var friends:List<String>?=null

    @SerializedName("chats")
    @Expose
    var chats:ArrayList<String>?=null

    @SerializedName("active")
    @Expose
    var active:Boolean?=null


    @SerializedName("stories")
    @Expose
    var stories:ArrayList<String>?=null

    var listStories:ArrayList<Stories> ?=null


    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        fullname = parcel.readString()
        avatar = parcel.readString()
        email=parcel.readString()
        message = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        friends = parcel.createStringArrayList()
        active = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        chats = parcel.createStringArrayList()
        stories = parcel.createStringArrayList()
        listStories=parcel.createTypedArrayList(Stories.CREATOR)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(fullname)
        parcel.writeString(avatar)
        parcel.writeString(email)
        parcel.writeValue(message)
        parcel.writeStringList(friends)
        parcel.writeValue(active)
        parcel.writeStringList(chats)
        parcel.writeStringList(stories)
        parcel.writeTypedList(listStories)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserModel> {
        override fun createFromParcel(parcel: Parcel): UserModel {
            return UserModel(parcel)
        }

        override fun newArray(size: Int): Array<UserModel?> {
            return arrayOfNulls(size)
        }
    }


}