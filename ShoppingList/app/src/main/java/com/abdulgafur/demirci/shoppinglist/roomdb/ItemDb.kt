package com.abdulgafur.demirci.shoppinglist.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.abdulgafur.demirci.shoppinglist.model.Item

@Database(entities = [Item::class], version = 1)
abstract class ItemDb : RoomDatabase() {
    abstract fun itemDao(): ItemDao
}