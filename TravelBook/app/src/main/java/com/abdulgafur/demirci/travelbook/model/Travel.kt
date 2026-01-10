package com.abdulgafur.demirci.travelbook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "travels")
class Travel(
    @ColumnInfo("travelName")
    var travelName: String,

    @ColumnInfo("travelLatitude")
    var travelLatitude: Double?,

    @ColumnInfo("travelLongitude")
    var travelLongitude: Double?,

    ) : Serializable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}