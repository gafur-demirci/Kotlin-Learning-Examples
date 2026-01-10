package com.abdulgafur.demirci.travelbook.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(navController: NavController) {
    var showDropDownMenu by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarColors(
            containerColor = Color.Blue,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.Cyan,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        title = {
            Row(modifier = Modifier.padding(start = 10.dp)) {
                Text("Travel Book")
            }
        },
        actions = {
            IconButton(onClick = { showDropDownMenu = true }) {
                Icon(Icons.Filled.MoreVert, null)
            }
            DropdownMenu(
                expanded = showDropDownMenu,
                onDismissRequest = { showDropDownMenu = false },
                offset = DpOffset((-40).dp, (-40).dp),
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Open Map") },
                    leadingIcon = { Icon(Icons.Filled.LocationOn, null) },
                    onClick = {
                        showDropDownMenu = false
                        navController.navigate("map_screen")
                    }
                )
            }
        }
    )
}