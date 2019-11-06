package com.example.clonemessandroid.data.model

import android.os.Parcel
import android.os.Parcelable

class Stories() : Parcelable{
    var uid:String?=null
    var img:String?=null
    var timestamp:Long?=null
    var listSeen:ArrayList<String>?=null

    constructor(parcel: Parcel) : this() {
        uid = parcel.readString()
        img = parcel.readString()
        timestamp = parcel.readValue(Long::class.java.classLoader) as? Long
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uid)
        parcel.writeString(img)
        parcel.writeValue(timestamp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Stories> {
        override fun createFromParcel(parcel: Parcel): Stories {
            return Stories(parcel)
        }

        override fun newArray(size: Int): Array<Stories?> {
            return arrayOfNulls(size)
        }
    }

}