package com.hugo.datasource.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao <in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t : T) : Long

    @Delete
    fun delete(t : T) : Int

    @Update
    fun update(t : T) : Int
}

// with <in>  T can only be used as input.
// for example:  fun insert(t : T) : Long
// with <out> T can  be used as input & output.
// for example:  fun insert(t : T) : T