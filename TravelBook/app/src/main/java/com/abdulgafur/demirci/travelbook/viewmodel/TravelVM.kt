package com.abdulgafur.demirci.travelbook.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.abdulgafur.demirci.travelbook.model.Travel
import com.abdulgafur.demirci.travelbook.roomdb.TravelDb
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TravelVM(application: Application) : AndroidViewModel(application) {
    val items = mutableStateOf<List<Travel>>(listOf())
    val item = mutableStateOf(Travel("",0.0,0.0))

    private val db = Room.databaseBuilder(
        getApplication(),
        TravelDb::class.java,
        "travels.db"
    ).build()

    private val travelDao = db.travelDao()

    fun getTravels() {
        viewModelScope.launch(Dispatchers.IO) {
            items.value = travelDao.getTravelWithNameAndId()
        }
    }

    fun getTravel(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = travelDao.getTravelById(id)
            item?.let {
                this@TravelVM.item.value = it
            }
        }
    }

    fun insertTravel(travel: Travel) {
        viewModelScope.launch(Dispatchers.IO) {
            travelDao.insertTravel(travel)
        }
    }

    fun deleteTravel(travel: Travel) {
        viewModelScope.launch(Dispatchers.IO) {
            travelDao.deleteTravel(travel)
        }
    }
}