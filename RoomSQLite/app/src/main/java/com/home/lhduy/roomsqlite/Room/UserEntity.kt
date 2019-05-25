package com.home.lhduy.roomsqlite.Room

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var name: String
):Parcelable{
    constructor(): this(null,"")
}