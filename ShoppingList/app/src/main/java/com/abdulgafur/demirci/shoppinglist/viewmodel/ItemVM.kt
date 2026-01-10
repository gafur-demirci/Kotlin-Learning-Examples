package com.abdulgafur.demirci.shoppinglist.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.abdulgafur.demirci.shoppinglist.model.Item
import com.abdulgafur.demirci.shoppinglist.roomdb.ItemDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ItemVM(application: Application) : AndroidViewModel(application) {

    val items = mutableStateOf<List<Item>>(listOf())
    val item = mutableStateOf<Item>(Item("","",0.0,ByteArray(0)))

    private val db = Room.databaseBuilder(
        getApplication(),
        ItemDb::class.java,
        "items.db"
    ).build()

    private val itemDao = db.itemDao()

    fun getItems() {
        viewModelScope.launch(Dispatchers.IO) {
            items.value = itemDao.getItemWithNameAndId()
        }
    }

    fun getItem(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = itemDao.getItemById(id)
            item?.let {
                this@ItemVM.item.value = it
            }
        }
    }

    fun insertItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.insertItem(item)
        }
    }

    fun deleteItem(item: Item) {
        viewModelScope.launch(Dispatchers.IO) {
            itemDao.deleteItem(item)
        }
    }

}