package com.abdulgafur.demirci.secretdiaries.data

import java.util.Date

data class Note(
    override val id: String,
    override val title: String,
    override val detail: String,
    override val createdDate: Date,
) : INote {

    override fun getNotes(): List<Note> {
        val notes: List<Note> = listOf(
            Note("0","test","test details", Date()),
            Note("1","test2","test details2", Date()),
            Note("2","test3","test details3", Date()),
            Note("3","test4","test details4", Date()),
            Note("4","test5","test details5", Date()),
            Note("5","test6","test details6", Date()),
            Note("6","test7","test details7", Date()),
            Note("7","test8","test details8", Date()),
        )

        return notes
    }

}