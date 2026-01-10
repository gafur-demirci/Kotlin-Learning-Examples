package com.abdulgafur.demirci.secretdiaries

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abdulgafur.demirci.secretdiaries.model.Note
import com.abdulgafur.demirci.secretdiaries.ui.theme.SecretDiariesTheme
import com.abdulgafur.demirci.secretdiaries.viewmodel.NoteVM
import com.abdulgafur.demirci.secretdiaries.views.AddNoteScreen
import com.abdulgafur.demirci.secretdiaries.views.NoteDetail
import com.abdulgafur.demirci.secretdiaries.views.Notes


class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<NoteVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val navController = rememberNavController()

            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                Box(modifier = Modifier.padding(innerPadding)) {
                    NavHost(navController = navController, startDestination = "list_screen") {

                        composable("list_screen") {
                            viewModel.getNotes()
                            val noteList by remember { viewModel.notes }
                            Notes(notes = noteList, navController = navController)
                        }

                        composable("add_note") {
                            AddNoteScreen(navController = navController, addNote = { note ->
                                viewModel.addNote(note)
                                navController.navigate("list_screen")
                            })
                        }

                        composable(
                            "note/{id}",
                            arguments = listOf(navArgument("id") {
                                type = NavType.StringType
                            })
                        ) {
                            val id = remember {
                                it.arguments?.getString("id")?.toIntOrNull() ?: 1
                            }

                            viewModel.getNote(id)
                            val selectedNote by remember { viewModel.note }

                            NoteDetail(note = selectedNote, navController = navController) {
                                viewModel.deleteNote(selectedNote)
                                navController.navigate("list_screen")
                            }
                        }
                    }
                }
            }
        }
    }
}