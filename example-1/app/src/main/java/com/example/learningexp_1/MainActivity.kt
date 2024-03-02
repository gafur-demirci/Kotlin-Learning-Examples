package com.example.learningexp_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.learningexp_1.ui.theme.LearningExp1Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LearningExp1Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    GreetingImg(message = getString(R.string.happy_birthday_sam),
//                        from = "From Emma",
//                        modifier = Modifier.padding(8.dp))
//                    ComposeTutorial(
//                        title = "Jetpack Compose tutorial",
//                        miniDesc = "Jetpack Compose is a modern toolkit for building native Android UI. Compose simplifies and accelerates UI development on Android with less code, powerful tools, and intuitive Kotlin APIs.",
//                        detail = "In this tutorial, you build a simple UI component with declarative functions. You call Compose functions to say what elements you want and the Compose compiler does the rest. Compose is built around Composable functions. These functions let you define your app\\'s UI programmatically because they let you describe how it should look and provide data dependencies, rather than focus on the process of the UI\\'s construction, such as initializing an element and then attaching it to a parent. To create a Composable function, you add the @Composable annotation to the function name.",
//                        modifier = Modifier
//                    )
//                    CenterTutorial(
//                        header = "All tasks completed",
//                        desc = "Nice work!",
//                        modifier = Modifier
//                    )
//                    QuadrantTutorial()
                    BusinessCard(
                        "Google",
                        "Everything Online!"
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Surface(color = Color.Cyan) {
        Text(
            text = "Hello $name!",
            modifier = modifier.padding(24.dp)
        )
    }

}

@Composable
fun GreetingText(message: String, from: String, modifier: Modifier) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Text(
            text = message,
            fontSize = 100.sp,
            lineHeight = 116.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = from,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@Composable
fun GreetingImg(message: String, from: String, modifier: Modifier) {
    val img = painterResource(id = R.drawable.androidparty)
    Box(modifier) {
        Image(
            painter = img,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alpha = 0.5F
        )
        GreetingText(
            message = message,
            from = from,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }

}

@Composable
fun ComposeTutorial(title: String, miniDesc: String, detail: String, modifier: Modifier) {
    val img = painterResource(id = R.drawable.bg_compose_background)
    Column(modifier) {
        Image(
            painter = img,
            contentDescription = null,
        )
        Text(
            text = title,
            fontSize = 24.sp,
            modifier = modifier.padding(16.dp)
        )
        Text(
            text = miniDesc,
            modifier = modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = detail,
            modifier = modifier.padding(16.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun CenterTutorial(header: String, desc: String, modifier: Modifier) {
    val img = painterResource(id = R.drawable.ic_task_completed)
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = img,
            contentDescription = null
        )
        Text(
            text = header,
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(top = 24.dp, bottom = 8.dp)
        )
        Text(
            text = desc,
            fontSize = 16.sp
        )
    }
}

@Composable
fun QuadrantTutorial() {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            QuadrantItem(
                header = "Text composable",
                desc = "Displays text and follows the recommended Material Design guidelines.",
                bgColor = Color(0xFFEADDFF),
                modifier = Modifier.weight(1f)
            )
            QuadrantItem(
                header = "Image composable",
                desc = "Creates a composable that lays out and draws a given Painter class object.",
                bgColor = Color(0xFFD0BCFF),
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)) {
            QuadrantItem(
                header = "Row composable",
                desc = "A layout composable that places its children in a horizontal sequence.",
                bgColor = Color(0xFFB69DF8),
                modifier = Modifier.weight(1f)
            )
            QuadrantItem(
                header = "Col composable",
                desc = "A layout composable that places its children in a vertical sequence.",
                bgColor = Color(0xFFF6EDFF),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun QuadrantItem(
    header: String,
    desc: String,
    bgColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = header,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = desc,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun BusinessCard(
    name: String,
    title: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Rounded.Favorite, contentDescription = null,
                Modifier
                    .width(50.dp)
                    .height(60.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title)
        }
    }
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center
    ) {
        BusinessInfo(modifier = Modifier)
    }
}

@Composable
fun BusinessInfo(modifier: Modifier) {

    Column(
        modifier = Modifier.padding(bottom = 4.dp),
//        horizontalArrangement = Arrangement.Absolute.Left,
//        verticalAlignment = Alignment.CenterVertically

    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Rounded.Phone, contentDescription = null)
            Text(
                text = "+90 (545) 248 25 35",
                textAlign = TextAlign.Justify,
                modifier= modifier.padding(start = 12.dp)
            )

        }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {

            Icon(Icons.Rounded.Favorite, contentDescription = null)
            Text(
                text = "@myChannelsFav",
                textAlign = TextAlign.Justify,
                modifier= modifier.padding(start = 12.dp)

            )
        }
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Rounded.Email, contentDescription = null)
            Text(
                text = "dummy@gmail.com.tr",
                textAlign = TextAlign.Justify,
                modifier= modifier.padding(start = 12.dp)

            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LearningExp1Theme {
        Greeting("World")
    }
}

@Preview(showBackground = true)
@Composable
fun BirthdayCardPreview() {
    LearningExp1Theme {
//        GreetingText(message = "Happy Birthday Sam!", from = "From Me")
        GreetingImg(message = "Happy Birthday Sam!", from = "From Me", modifier = Modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun Quadrant() {
    LearningExp1Theme {
        QuadrantTutorial()
    }
}

@Preview(showBackground = true)
@Composable
fun BusinesCard() {
    LearningExp1Theme {
        BusinessCard(name = "Google", title = "Everybody does")
    }
}