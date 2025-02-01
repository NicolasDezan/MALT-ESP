package com.nicolas.maltesp.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MaltingRecipe::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun maltingRecipeDao(): MaltingRecipeDao
}