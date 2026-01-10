package com.abdulgafur.demirci.shoppinglist.screens

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abdulgafur.demirci.shoppinglist.R
import com.abdulgafur.demirci.shoppinglist.model.Item
import com.abdulgafur.demirci.shoppinglist.ui.theme.ShoppingListTheme

@Composable
fun DetailScreen(item: Item, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = item.itemName,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(2.dp)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            val imageBitmap = item.image?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)?.asImageBitmap()
            }

            Image(imageBitmap ?: ImageBitmap.imageResource(R.drawable.not_found),
                contentDescription = item.itemName,
                modifier = Modifier
                    .padding(2.dp)
                    .size(300.dp, 200.dp)
            )

            Spacer(modifier = Modifier.padding(20.dp))
            
            Text(
                text = item.storeName ?: "",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Normal,
                modifier = Modifier.padding(2.dp)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Text(
                text = item.price.toString(),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(2.dp)
            )

            Spacer(modifier = Modifier.padding(20.dp))

            Button(
                onClick = onDelete
            ) {
                Text(
                    text = "Delete"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ShoppingListTheme {
        DetailScreen(item = Item("test", "test", 1.0, null)) {
            println("")
        }
    }
}