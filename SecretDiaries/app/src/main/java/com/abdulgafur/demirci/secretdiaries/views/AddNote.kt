package com.abdulgafur.demirci.secretdiaries.views

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdulgafur.demirci.secretdiaries.data.Note
import com.abdulgafur.demirci.secretdiaries.views.ui.theme.SecretDiariesTheme
import java.util.Date
import java.util.UUID
import kotlin.uuid.Uuid

class AddNote : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SecretDiariesTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

    var title by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Add Your Daily Note",
            modifier = modifier
        )
        OutlinedTextField(
            value = title,
            label = { Text("Note Title") },
            onValueChange = { title = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            enabled = true

        )
        OutlinedTextField(
            value = detail,
            label = { Text("Note Detail") },
            onValueChange = { detail = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(100.dp),
            enabled = true
        )
        Button(
            onClick = {
                println("Note saved")
                if (title.isNotEmpty() && detail.isNotEmpty()) {
                    val note = Note(
                        id = UUID.randomUUID().toString(),
                        title = title,
                        detail = detail,
                        createdDate = Date()
                    )
                    println(note)
                }

            }
        ) {
            Text("Save")
        }
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview3() {
    SecretDiariesTheme {
        Greeting("Android")
    }
}