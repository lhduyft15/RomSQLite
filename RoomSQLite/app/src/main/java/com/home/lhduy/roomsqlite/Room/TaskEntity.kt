package com.home.lhduy.roomsqlite.Room

//Parcelable
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
//

@Parcelize
@Entity
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    var description: String,
    var name: String,
    var completed: Boolean,
    var userNameAssigned: String

) : Parcelable {
    constructor() : this(null,"","",false,"")
}
