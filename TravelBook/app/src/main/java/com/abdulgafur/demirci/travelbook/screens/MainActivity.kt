package com.abdulgafur.demirci.travelbook.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abdulgafur.demirci.travelbook.model.Travel
import com.abdulgafur.demirci.travelbook.ui.theme.TravelBookTheme
import com.abdulgafur.demirci.travelbook.viewmodel.TravelVM

class MainActivity : ComponentActivity() {

    private val viewModel : TravelVM by viewModels<TravelVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            TravelBookTheme {
                Box(modifier = Modifier.padding(0.dp)) {
                    NavHost(navController = navController, startDestination = "list_screen") {

                        composable("list_screen") {
                            viewModel.getTravels()
                            val items by remember { viewModel.items }
                            ItemList(itemList = items, navController = navController)
                        }

                        composable("map_screen") {
                            MountainMap(
                                modifier = Modifier.fillMaxWidth(),
                                isOld = false
                            ) {
                                it?.let {
                                    viewModel.insertTravel(it)
                                    navController.navigate("list_screen")
                                    println(it)
                                }
                            }
                        }

                        composable(
                            "detail_screen/{id}",
                            arguments = listOf(navArgument("id") { })
                        ) {
                            val id = remember {
                                it.arguments?.getString("id")?.toIntOrNull() ?: 1
                            }

                            viewModel.getTravel(id)
                            val selectedItem by remember { viewModel.item }

                            MountainMap(
                                modifier = Modifier.fillMaxWidth(),
                                isOld = true,
                                selectedTravel = selectedItem,

                                ) {
                                viewModel.deleteTravel(selectedItem)
                                println(it)
                                navController.navigate("list_screen")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TravelBookTheme {
        ItemList(listOf(), rememberNavController())
    }
}