package com.abdulgafur.demirci.secretdiaries.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.abdulgafur.demirci.secretdiaries.components.TopBar
import com.abdulgafur.demirci.secretdiaries.model.Note
import com.abdulgafur.demirci.secretdiaries.ui.theme.SecretDiariesTheme
import java.util.Date


@Composable
fun AddNoteScreen(navController: NavController, addNote: (Note) -> Unit) {

    var title by remember { mutableStateOf("") }
    var detail by remember { mutableStateOf("") }

    TopBar(navController)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopStart
    ) {

        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(navController)
            Text(
                text = "Add Your Note",
                fontSize = 24.sp,
                modifier = Modifier.padding(12.dp),
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
            Spacer(Modifier.padding(12.dp).weight(1f))
            Button(
                onClick = {
                    println("Note saved")
                    if (title.isNotEmpty() && detail.isNotEmpty()) {
                        val note = Note(
                            title = title,
                            detail = detail,
                            createdDate = Date().toString()
                        )
                        println(note)
                        addNote(note)
                    }

                }
            ) {
                Text("Save")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview3() {
    SecretDiariesTheme {
        AddNoteScreen(navController = NavController(LocalContext.current)) {

        }
    }
}