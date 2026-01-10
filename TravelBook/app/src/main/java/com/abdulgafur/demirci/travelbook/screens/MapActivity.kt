package com.abdulgafur.demirci.travelbook.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdulgafur.demirci.travelbook.model.Travel
import com.abdulgafur.demirci.travelbook.ui.theme.TravelBookTheme
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.GoogleMapComposable
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberMarkerState

class MapActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TravelBookTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MountainMap(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxWidth(),
                        isOld = true
                    ) {
                        println("")
                    }
                }
            }
        }
    }
}

@Composable
fun MountainMap(
    modifier: Modifier,
    isOld: Boolean,
    selectedTravel: Travel? = null,
    callback: (travel: Travel?) -> Unit
) {
    var isMapLoaded by remember { mutableStateOf(false) }
    var isMapLongClicked by remember { mutableStateOf(false) }
    var travelName by remember { mutableStateOf("") }
    var travelLatitude by remember { mutableStateOf(null) }
    var travelLongitude by remember { mutableStateOf(null) }
    var markerPosition by remember { mutableStateOf<LatLng?>(null) }

    Box(
        modifier = modifier
    ) {
        // Add GoogleMap here
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                GoogleMap(
                    onMapLoaded = {
                        isMapLoaded = true
                    },
                    onMapLongClick = {
                        isMapLongClicked = true
                        println(it.latitude)
                        println(it.longitude)
                        markerPosition = it
                    }
                ) {
                    if (isOld && isMapLoaded && !isMapLongClicked) {
                        if (selectedTravel != null) {
                            LoadMarker(selectedTravel)
                        }
                    } else {
                        markerPosition?.let {
                            Marker(
                                state = MarkerState(position = it)
                            )
                        }
                    }

                }
            }

            if (isOld) {
                Text(
                    text = selectedTravel?.travelName ?: "No Travel Selected",
                    modifier = Modifier.padding(16.dp)
                )
                Button(
                    onClick = {
                        // Handle button click
                        println(selectedTravel?.travelName)
                        println(selectedTravel?.travelLatitude)
                        println(selectedTravel?.travelLongitude)
                        callback(null)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .height(48.dp)
                ) {
                    Text(
                        "Delete"
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    OutlinedTextField(
                        value = travelName,
                        onValueChange = {
                            travelName = it
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        label = { Text("Enter Travel Name") }
                    )

                    Button(
                        onClick = {
                            // Handle button click
                            println(travelName)
                            callback(Travel(travelName, markerPosition?.latitude, markerPosition?.longitude))
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(48.dp),
                        enabled = isMapLongClicked && travelName.isNotEmpty()
                    ) {
                        Text(
                            "Save"
                        )
                    }
                }
            }
        }
    }
}

@Composable
@GoogleMapComposable
fun LoadMarker(selectedTravel: Travel) {
    Marker(
        state = rememberMarkerState(position = LatLng(
            selectedTravel.travelLatitude ?: 0.0,
            selectedTravel.travelLongitude ?: 0.0
        )),
        title = selectedTravel.travelName
    )
}

@Preview(showBackground = true)
@Composable
fun GoogleMapPreview() {
    TravelBookTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            MountainMap(
                modifier = Modifier.padding(innerPadding),
                isOld = true
            ) {
                println("")
            }
        }
    }
}