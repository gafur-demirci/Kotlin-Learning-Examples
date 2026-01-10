package com.abdulgafur.demirci.travelbook.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdulgafur.demirci.travelbook.model.Travel

@Database(entities = [Travel::class], version = 1)
abstract class TravelDb : RoomDatabase() {
    abstract fun travelDao(): TravelDao
}