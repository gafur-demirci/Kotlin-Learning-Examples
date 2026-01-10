package com.abdulgafur.demirci.travelbook.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdulgafur.demirci.travelbook.components.AppBar
import com.abdulgafur.demirci.travelbook.model.Travel


@Composable
fun ItemList(itemList: List<Travel>, navController: NavController) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppBar(navController)
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
            ) {
                items(itemList) {
                    ItemRow(item = it, navController)
                }
            }
        }
    )
}

@Composable
fun ItemRow(item: Travel, navController: NavController) {
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(color = MaterialTheme.colorScheme.surface)
        .clickable {
            navController.navigate("detail_screen/${item.id}")
        }
    ) {
        Text(
            text = item.travelName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(10.dp),
            fontWeight = MaterialTheme.typography.titleLarge.fontWeight
        )
    }
}