package com.abdulgafur.demirci.secretdiaries.data

import java.util.Date

interface INote {
    val id: String
    val title: String
    val detail: String
    val createdDate: Date

    fun getNotes(): List<Note>

}