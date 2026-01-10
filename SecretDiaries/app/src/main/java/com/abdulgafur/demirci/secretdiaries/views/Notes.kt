package com.abdulgafur.demirci.secretdiaries.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdulgafur.demirci.secretdiaries.components.TopBar
import com.abdulgafur.demirci.secretdiaries.model.Note

@Composable
fun Notes(notes: List<Note>, navController: NavController) {
    Column {
        TopBar(navController)
        LazyColumn(Modifier.fillMaxSize()) {
            items(items = notes) { note ->
                NoteItem(note, navController)
            }
        }
    }
}

@Composable
fun NoteItem(note: Note, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = CardDefaults.cardElevation(12.dp),
        onClick = {
            println(note.id)
            navController.navigate("note/${note.id}")
        }
    ) {
        Row(modifier = Modifier.padding(5.dp)) {
            Text(
                text = note.title,
                modifier = Modifier.padding(10.dp, 20.dp)
            )
        }
    }
}