package com.abdulgafur.demirci.shoppinglist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
class Item(
    @ColumnInfo("name")
    var itemName: String,

    @ColumnInfo("store")
    var storeName: String?,

    @ColumnInfo("price")
    var price: Double?,

    @ColumnInfo("image")
    var image: ByteArray?
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}