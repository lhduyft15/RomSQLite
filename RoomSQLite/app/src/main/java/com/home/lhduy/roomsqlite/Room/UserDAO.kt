package com.home.lhduy.roomsqlite.Room

import android.arch.persistence.room.*

@Dao
interface UserDAO {
    @Query("SELECT * FROM UserEntity")
    fun getAll(): List<UserEntity>

    @Query("SELECT * FROM UserEntity WHERE id=:id")
    fun findById(id : Int) : UserEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj : UserEntity): Long

    @Query("DELETE FROM UserEntity WHERE id=:id")
    fun deleteById(id : Int)

    @Delete
    fun delete(todo: UserEntity)

    @Update
    fun update(user : UserEntity)

    @Query("DELETE FROM UserEntity")
    fun deleteAllStudent()

}