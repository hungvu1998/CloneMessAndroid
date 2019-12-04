package com.example.clonemessandroid.data.model

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Stories() : Parcelable{

    @SerializedName("storyId")
    @Expose
    var storyId:String?=null
    @SerializedName("imagePath")
    @Expose
    var imagePath:String?=null
    @SerializedName("listView")
    @Expose
    var listView:ArrayList<String>?=null
    @SerializedName("timestamp")
    @Expose
    var timestamp:Long?=null


    constructor(parcel: Parcel) : this() {
        storyId = parcel.readString()
        imagePath = parcel.readString()
        timestamp = parcel.readValue(Long::class.java.classLoader) as? Long
        listView = parcel.createStringArrayList()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(storyId)
        parcel.writeString(imagePath)
        parcel.writeValue(timestamp)
        parcel.writeStringList(listView)
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