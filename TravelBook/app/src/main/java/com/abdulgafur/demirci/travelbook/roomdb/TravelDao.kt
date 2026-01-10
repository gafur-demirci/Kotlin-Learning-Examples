package com.abdulgafur.demirci.travelbook.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.abdulgafur.demirci.travelbook.model.Travel

@Dao
interface TravelDao {

    @Query("SELECT travelName, id FROM travels")
    fun getTravelWithNameAndId(): List<Travel>

    @Query("SELECT * FROM travels WHERE id = :id")
    fun getTravelById(id: Int): Travel?

    @Insert
    suspend fun insertTravel(item: Travel)

    @Delete
    suspend fun deleteTravel(item: Travel)
}