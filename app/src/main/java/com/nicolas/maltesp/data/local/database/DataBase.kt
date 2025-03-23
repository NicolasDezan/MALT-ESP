package com.nicolas.maltesp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nicolas.maltesp.data.local.entities.MaltingRecipe
import com.nicolas.maltesp.data.local.dao.MaltingRecipeDao

@Database(entities = [MaltingRecipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun maltingRecipeDao(): MaltingRecipeDao
}