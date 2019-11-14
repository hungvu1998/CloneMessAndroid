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


    @SerializedName("message")
    @Expose
    var message:Boolean?=null

    @SerializedName("friends")
    @Expose
    var friends:List<String>?=null

    @SerializedName("chats")
    @Expose
    var chats:List<String>?=null

    @SerializedName("active")
    @Expose
    var active:Boolean?=null

    constructor(parcel: Parcel) : this() {
        username = parcel.readString()
        fullname = parcel.readString()
        avatar = parcel.readString()
        message = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        friends = parcel.createStringArrayList()
        active = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        chats = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(fullname)
        parcel.writeString(avatar)
        parcel.writeValue(message)
        parcel.writeStringList(friends)
        parcel.writeValue(active)
        parcel.writeStringList(chats)
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