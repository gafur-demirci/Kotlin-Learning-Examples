package com.abdulgafur.demirci.superherobook

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson

@Composable
fun SuperheroList(superheros: List<Superhero>, navController: NavController) {
    LazyColumn(
        contentPadding = PaddingValues(5.dp),
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        items(superheros) {
            SuperheroRow(superhero = it, navController = navController)
        }
    }
}

@Composable
fun SuperheroRow(superhero: Superhero, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable {
                navController.navigate("hero_detail/${Gson().toJson(superhero)}")
            }
    ) {
        Text(superhero.name,
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold)
        Text(superhero.universe,
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Medium)
    }
}