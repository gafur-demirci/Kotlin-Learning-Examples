package com.abdulgafur.demirci.secretdiaries.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "notes")
class Note(

    @ColumnInfo("title")
    var title: String,

    @ColumnInfo("detail")
    var detail: String?,

    @ColumnInfo("createdDate")
    var createdDate: String?

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}