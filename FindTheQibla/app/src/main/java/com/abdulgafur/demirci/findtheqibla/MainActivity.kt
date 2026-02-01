package com.abdulgafur.demirci.findtheqibla

import android.Manifest
import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abdulgafur.demirci.findtheqibla.ui.theme.FindTheQiblaTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import kotlin.math.*

class MainActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private var magnetometer: Sensor? = null

    private var gravity = FloatArray(3)
    private var geomagnetic = FloatArray(3)
    private var hasGravity = false
    private var hasGeomagnetic = false

    private val _azimuth = mutableFloatStateOf(0f)
    private val _qiblaDirection = mutableFloatStateOf(0f)
    private val _userLocation = mutableStateOf<Location?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)

        enableEdgeToEdge()
        setContent {
            FindTheQiblaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    QiblaScreen(
                        azimuth = _azimuth.floatValue,
                        qiblaDirection = _qiblaDirection.floatValue,
                        userLocation = _userLocation.value,
                        onPermissionGranted = { fetchLocation() }
                    )
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
        magnetometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values.clone() // clone() kullanmak verinin değişmesini önler
            hasGravity = true
        }
        if (event.sensor.type == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values.clone()
            hasGeomagnetic = true
        }

        if (hasGravity && hasGeomagnetic) {
            val r = FloatArray(9)
            val i = FloatArray(9)
            if (SensorManager.getRotationMatrix(r, i, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(r, orientation)
                var azimuthInDegrees = Math.toDegrees(orientation[0].toDouble()).toFloat()
                if (azimuthInDegrees < 0) azimuthInDegrees += 360f
                _azimuth.floatValue = azimuthInDegrees
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    @SuppressLint("MissingPermission")
    private fun fetchLocation() {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                updateLocationInfo(location)
            } else {
                fusedLocationClient.getCurrentLocation(
                    Priority.PRIORITY_HIGH_ACCURACY,
                    CancellationTokenSource().token
                ).addOnSuccessListener { currentLocation ->
                    currentLocation?.let { updateLocationInfo(it) }
                }
            }
        }
    }

    private fun updateLocationInfo(location: Location) {
        _userLocation.value = location
        _qiblaDirection.floatValue = calculateQiblaDirection(location.latitude, location.longitude)
    }

    private fun calculateQiblaDirection(lat: Double, lon: Double): Float {
        val kaabaLat = Math.toRadians(21.422487)
        val kaabaLon = Math.toRadians(39.826206)
        val userLat = Math.toRadians(lat)
        val userLon = Math.toRadians(lon)
        val deltaLon = kaabaLon - userLon
        val y = sin(deltaLon)
        val x = cos(userLat) * tan(kaabaLat) - sin(userLat) * cos(deltaLon)
        var qiblaDegree = Math.toDegrees(atan2(y, x)).toFloat()
        if (qiblaDegree < 0) qiblaDegree += 360f
        return qiblaDegree
    }
}

@SuppressLint("DefaultLocale")
@Composable
fun QiblaScreen(
    azimuth: Float,
    qiblaDirection: Float,
    userLocation: Location?,
    onPermissionGranted: () -> Unit
) {
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            onPermissionGranted()
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Kıble Bulucu",
                fontSize = 32.sp,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = Modifier.height(48.dp))

            if (userLocation == null) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Konum alınıyor...", color = MaterialTheme.colorScheme.onBackground)
            } else {
                CompassView(azimuth, qiblaDirection)
                Spacer(modifier = Modifier.height(32.dp))
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = "Kıble Açısı: ${qiblaDirection.toInt()}°",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                    Text(
                        text = "Lat: ${String.format("%.4f", userLocation.latitude)}, Lon: ${String.format("%.4f", userLocation.longitude)}",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}

@Composable
fun CompassView(azimuth: Float, qiblaDirection: Float) {
    val animatedAzimuth by animateFloatAsState(targetValue = -azimuth, label = "azimuth")
    val qiblaRotation = qiblaDirection + animatedAzimuth

    val textMeasurer = rememberTextMeasurer()
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outline

    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(320.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val center = Offset(size.width / 2, size.height / 2)
            val radius = size.minDimension / 2

            drawCircle(
                color = outlineColor,
                radius = radius,
                center = center,
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
            )

            rotate(degrees = animatedAzimuth, pivot = center) {
                for (i in 0 until 360 step 10) {
                    val angle = Math.toRadians(i.toDouble())
                    val isMajor = i % 30 == 0
                    val tickLen = if (isMajor) 15.dp.toPx() else 8.dp.toPx()

                    val startX = center.x + radius * sin(angle).toFloat()
                    val startY = center.y - radius * cos(angle).toFloat()
                    val endX = center.x + (radius - tickLen) * sin(angle).toFloat()
                    val endY = center.y - (radius - tickLen) * cos(angle).toFloat()

                    drawLine(
                        color = outlineColor.copy(alpha = 0.6f),
                        start = Offset(startX, startY),
                        end = Offset(endX, endY),
                        strokeWidth = if (isMajor) 2.dp.toPx() else 1.dp.toPx()
                    )

                    if (isMajor && i % 90 != 0) {
                        val textRadius = radius - 35.dp.toPx()
                        val tx = center.x + textRadius * sin(angle).toFloat()
                        val ty = center.y - textRadius * cos(angle).toFloat()

                        rotate(degrees = -animatedAzimuth - i, pivot = Offset(tx, ty)) {
                            val textLayoutResult = textMeasurer.measure(
                                text = i.toString(),
                                style = TextStyle(color = onSurfaceColor.copy(alpha = 0.7f), fontSize = 12.sp)
                            )
                            drawText(
                                textLayoutResult = textLayoutResult,
                                topLeft = Offset(tx - textLayoutResult.size.width / 2, ty - textLayoutResult.size.height / 2)
                            )
                        }
                    }
                }

                val directions = listOf("N" to 0f, "E" to 90f, "S" to 180f, "W" to 270f)
                directions.forEach { (label, angle) ->
                    val rad = Math.toRadians(angle.toDouble())
                    val x = center.x + (radius - 35.dp.toPx()) * sin(rad).toFloat()
                    val y = center.y - (radius - 35.dp.toPx()) * cos(rad).toFloat()

                    rotate(degrees = -animatedAzimuth - angle, pivot = Offset(x, y)) {
                        val textLayoutResult = textMeasurer.measure(
                            text = label,
                            style = TextStyle(
                                color = if (label == "N") Color.Red else onSurfaceColor,
                                fontSize = 20.sp,
                                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                            )
                        )
                        drawText(
                            textLayoutResult = textLayoutResult,
                            topLeft = Offset(x - textLayoutResult.size.width / 2, y - textLayoutResult.size.height / 2)
                        )
                    }
                }
            }

            rotate(degrees = qiblaRotation, pivot = center) {
                val needlePath = androidx.compose.ui.graphics.Path().apply {
                    moveTo(center.x, center.y - radius + 55.dp.toPx())
                    lineTo(center.x - 14.dp.toPx(), center.y)
                    lineTo(center.x + 14.dp.toPx(), center.y)
                    close()
                }
                drawPath(needlePath, Color.Red)

                val bottomNeedlePath = androidx.compose.ui.graphics.Path().apply {
                    moveTo(center.x, center.y + radius - 55.dp.toPx())
                    lineTo(center.x - 14.dp.toPx(), center.y)
                    lineTo(center.x + 14.dp.toPx(), center.y)
                    close()
                }
                drawPath(bottomNeedlePath, onSurfaceColor.copy(alpha = 0.8f))

                drawCircle(color = primaryColor, radius = 6.dp.toPx(), center = center)
                drawCircle(color = Color.White, radius = 2.dp.toPx(), center = center)
            }
        }
    }
}