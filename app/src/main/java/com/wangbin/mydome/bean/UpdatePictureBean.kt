package com.wangbin.mydome.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by  WangBin.
 * on 2018/8/2
 */

class UpdatePictureBean() : Parcelable {

    var path: String = ""

    var type: String = ""

    var name: String = ""

    var oldname: String = ""

    var size: String = ""

    constructor(parcel: Parcel) : this() {
        path = parcel.readString()
        type = parcel.readString()
        name = parcel.readString()
        oldname = parcel.readString()
        size = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(path)
        parcel.writeString(type)
        parcel.writeString(name)
        parcel.writeString(oldname)
        parcel.writeString(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UpdatePictureBean> {
        override fun createFromParcel(parcel: Parcel): UpdatePictureBean {
            return UpdatePictureBean(parcel)
        }

        override fun newArray(size: Int): Array<UpdatePictureBean?> {
            return arrayOfNulls(size)
        }
    }
}