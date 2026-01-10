package com.abdulgafur.demirci.secretdiaries.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.abdulgafur.demirci.secretdiaries.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavController) {
    var showDropDownMenu by remember { mutableStateOf(false) }

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth(),
        colors = TopAppBarColors(
            containerColor = Color.Blue,
            scrolledContainerColor = Color.Transparent,
            navigationIconContentColor = Color.White,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        navigationIcon = {
            IconButton(onClick = {
                navController.popBackStack()
            }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, null)
            }
        },
        title = {
            Text(
                text = stringResource(R.string.app_name),
            )
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
                    text = { Text(text = "Add Note") },
                    leadingIcon = { Icon(Icons.Filled.Create, null) },
                    onClick = {
                        println("Add Note")
                        showDropDownMenu = false
                        navController.navigate("add_note")
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun TopBarPreview() {
    TopBar(navController = NavController(LocalContext.current))
}