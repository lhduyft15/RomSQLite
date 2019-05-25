package com.home.lhduy.roomsqlite.Room

import android.arch.persistence.room.*

@Dao
interface TaskDAO{
    @Query("SELECT * FROM taskentity")
    fun getAll(): List<TaskEntity>

    @Query("SELECT * FROM taskentity WHERE id=:id")
    fun findById(id: Int) : TaskEntity


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj : TaskEntity): Long

    @Delete
    fun delete(todo: TaskEntity)

    @Query("DELETE FROM taskentity WHERE id=:id")
    fun deleteById(id:Int)

    @Update
    fun update(task : TaskEntity)

}
