package com.jeremieguillot.butterfly.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ButterflyDao {

    @Query("SELECT * FROM butterfly")
    suspend fun getAllButterflies(): ButterflyEntity

    @Query("SELECT * FROM butterfly WHERE id = :id")
    suspend fun getButterfly(id: String): ButterflyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertButterfly(userProfile: ButterflyEntity)

    @Delete
    suspend fun delete(user: ButterflyEntity)
}