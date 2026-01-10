package com.abdulgafur.demirci.shoppinglist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.abdulgafur.demirci.shoppinglist.screens.AddItemScreen
import com.abdulgafur.demirci.shoppinglist.screens.DetailScreen
import com.abdulgafur.demirci.shoppinglist.screens.ItemList
import com.abdulgafur.demirci.shoppinglist.ui.theme.ShoppingListTheme
import com.abdulgafur.demirci.shoppinglist.viewmodel.ItemVM

class MainActivity : ComponentActivity() {

    private val viewModel : ItemVM by viewModels<ItemVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()

            ShoppingListTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        NavHost(navController = navController, startDestination = "list_screen") {
                            composable("list_screen") {
                                viewModel.getItems()
                                val items by remember { viewModel.items }
                                ItemList(itemList = items, navController = navController)
                            }

                            composable("add_item_screen") {
                                AddItemScreen(saveItem = { item ->
                                    viewModel.insertItem(item)
                                    navController.navigate("list_screen")
                                })
                            }

                            composable(
                                "detail_screen/{id}",
                                arguments = listOf(navArgument("id") {
                                    type = NavType.StringType
                                })
                            ) {
                                val id = remember {
                                  it.arguments?.getString("id")?.toIntOrNull() ?: 1
                                }

                                viewModel.getItem(id)
                                val selectedItem by remember { viewModel.item }

                                DetailScreen(item = selectedItem) {
                                    viewModel.deleteItem(selectedItem)
                                    navController.navigate("list_screen")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}