package com.abdulgafur.demirci.secretdiaries.views

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdulgafur.demirci.secretdiaries.data.Note
import com.abdulgafur.demirci.secretdiaries.ui.theme.SecretDiariesTheme
import java.util.Date
import java.util.UUID


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SecretDiariesTheme {
                MainView(
                    context = LocalContext.current
                )
            }
        }
    }
}

@Composable
fun MainView(context: Context) {

    val notes = Note(UUID.randomUUID().toString(),"","", Date()).getNotes()

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(modifier= Modifier, onClick = {
            println("İntent start")
            context.startActivity(Intent(context, AddNote::class.java))
        }) {
            Text("Date Selection")
        }
        DailyNotes(notes)
    }
}

@Composable
fun DailyNotes(dailyNotes: List<Note>) {
    LazyColumn(Modifier.fillMaxSize()) {
        items(items = dailyNotes) {
            DailyNoteItem(it)
        }
    }
}

@Composable
fun DailyNoteItem(note: Note) {
    Card(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp).fillMaxWidth(),
        shape = RoundedCornerShape(CornerSize(10.dp)),
        elevation = CardDefaults.cardElevation(12.dp),
        onClick = {
            println(note.id)
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    SecretDiariesTheme {
        MainView(
            context = LocalContext.current
        )
    }
}