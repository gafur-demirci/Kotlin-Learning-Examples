package com.abdulgafur.demirci.shoppinglist.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abdulgafur.demirci.shoppinglist.model.Item

@Dao
interface ItemDao {

    @Query("SELECT name, id FROM items")
    fun getItemWithNameAndId(): List<Item>

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItemById(id: Int): Item?

    @Insert
    suspend fun insertItem(item: Item)

    @Delete
    suspend fun deleteItem(item: Item)

}