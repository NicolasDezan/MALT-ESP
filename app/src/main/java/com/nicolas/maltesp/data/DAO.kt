package com.nicolas.maltesp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MaltingRecipeDao {
    @Query("SELECT * FROM maltingrecipe")
    fun getAll(): List<MaltingRecipe>

    @Query("SELECT * FROM MaltingRecipe WHERE uid = :uid LIMIT 1")
    suspend fun getRecipeById(uid: Int): MaltingRecipe

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg recipes: MaltingRecipe)

    @Query("SELECT recipeName FROM MaltingRecipe")
    suspend fun getAllRecipeNames(): List<String>

    @Query("SELECT uid FROM MaltingRecipe WHERE recipeName = :name LIMIT 1")
    suspend fun getUidByName(name: String): Int?

    @Query("DELETE FROM MaltingRecipe WHERE uid = :uid")
    suspend fun deleteByUid(uid: Int)
}