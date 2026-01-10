package com.abdulgafur.demirci.secretdiaries.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
fun NoteDetail(note: Note, navController: NavController, onDelete: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopStart
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TopBar(navController)
            Text(
                text = note.title,
                fontSize = 30.sp,
                modifier = Modifier.padding(12.dp)
            )
            OutlinedTextField(
                value = note.detail!!,
                label = { Text("Note Detail") },
                readOnly = true,
                onValueChange = {  },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .height(100.dp),
                enabled = true
            )
            Button(
                onClick = onDelete
            ) {
                Text(
                    text = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteDetailPreview() {
    SecretDiariesTheme {
        NoteDetail(
            note = Note("Test", "test2", Date().toString()),
            navController = NavController(LocalContext.current)
        ) {

        }
    }
}