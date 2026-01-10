package com.abdulgafur.demirci.shoppinglist.screens

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.scale
import coil3.compose.rememberAsyncImagePainter
import com.abdulgafur.demirci.shoppinglist.R
import com.abdulgafur.demirci.shoppinglist.model.Item
import com.abdulgafur.demirci.shoppinglist.ui.theme.ShoppingListTheme
import java.io.ByteArrayOutputStream

@Composable
fun AddItemScreen(saveItem : (Item) -> Unit) {

    val itemName = remember { mutableStateOf("") }
    val storeName = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    var imageUri = remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            ImagePicker(onImageSelected = { uri ->
                imageUri.value = uri
            })

            TextField(
                value = itemName.value,
                onValueChange = { itemName.value = it },
                label = { Text("Enter Item Name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )
            TextField(
                value = storeName.value,
                onValueChange = { storeName.value = it },
                label = { Text("Enter Store Name") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )

            )
            TextField(
                value = price.value,
                onValueChange = { price.value = it },
                label = { Text("Enter Item Price") },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )
            )

            Button(
                onClick = {

                    val imageByteArray = imageUri.value?.let {
                        resizeImage(context = context, imageUri = it, 600, 400)
                    } ?: ByteArray(0)


                    val item = Item(
                        itemName = itemName.value,
                        storeName = storeName.value,
                        price = price.value.toDoubleOrNull(),
                        image = imageByteArray
                    )
                    saveItem(item)
                }
            )
            {
                Text(text = "Add Item")
            }
        }
    }
}


@Composable
fun ImagePicker(onImageSelected: (Uri) -> Unit) {
    // Implement image picker logic here
    var selectedImgUri by remember { mutableStateOf<Uri?>(null) }

    val context = LocalContext.current
    
    val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    val galleryLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->
        selectedImgUri = uri
    }

    val permissionLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            galleryLauncher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }
    // You can use a library like Coil or Glide to load and display images
    // Update the imageUri state with the selected image's URI
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        selectedImgUri?.let {
            Image(painter = rememberAsyncImagePainter(model = it),
                contentDescription = "Selected Image",
                modifier = Modifier
                    .size(300.dp, 200.dp)
                    .padding(16.dp)
            )

            onImageSelected(it)
        } ?: Image(
            painter = painterResource(R.drawable.not_selected),
            contentDescription = "Selected Image",
            modifier = Modifier.fillMaxWidth()
                .size(300.dp, 200.dp)
                .padding(16.dp)
                .clickable {
                    if(ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                        galleryLauncher.launch("image/*")
                    } else {
                        permissionLauncher.launch(permission)
                    }
                }
        )

    }

}

fun resizeImage(context : Context, imageUri : Uri, maxWidth : Int, maxHeight : Int) : ByteArray? {
    return try {
        val inputStream = context.contentResolver.openInputStream(imageUri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)

        val ratio = originalBitmap.width.toFloat() / originalBitmap.height.toFloat()
        var resizedWidth = maxWidth
        var resizedHeight = (resizedWidth / ratio).toInt()

        if(resizedHeight > maxHeight) {
            resizedHeight = maxHeight
            resizedWidth = (resizedHeight * ratio).toInt()
        }

//        val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, resizedWidth, resizedHeight, false)
        val resizedBitmap = originalBitmap.scale(resizedWidth, resizedHeight, false)

        val byteArrayOutputStream = ByteArrayOutputStream()
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        byteArrayOutputStream.toByteArray()
    } catch (e : Exception) {
        e.printStackTrace()
        null
    }
}

@Preview(showBackground = true)
@Composable
fun AddItemScreenPreview() {
    ShoppingListTheme {
        AddItemScreen {
            println("")
        }
    }
}