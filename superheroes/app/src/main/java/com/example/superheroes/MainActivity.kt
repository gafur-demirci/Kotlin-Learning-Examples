package com.example.superheroes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.superheroes.model.Hero
import com.example.superheroes.model.heroes
import com.example.superheroes.ui.theme.Shapes
import com.example.superheroes.ui.theme.SuperheroesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SuperheroesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SuperHeroesApp()
                }
            }
        }
    }
}

@Composable
fun SuperHeroesApp() {
    Scaffold(
        topBar = {
            HeroTopAppBar(modifier = Modifier)
        }
    ) { it ->
        LazyColumn(contentPadding = it) {
            items(heroes) {
                SuperHeroesItem(
                    hero = it,
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroTopAppBar(modifier: Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Superheroes",
                    style = MaterialTheme.typography.displayLarge
                )
            }
        },
        modifier = modifier
    )
}

@Composable
fun SuperHeroesItem(
    hero: Hero,
    modifier: Modifier = Modifier
) {

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(2.dp),
        shape = Shapes.medium
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .sizeIn(minHeight = 72.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SuperHeroInfo(name = hero.name, desc = hero.desc, modifier = modifier.width(260.dp))
            Spacer(Modifier.width(16.dp))
            SuperHeroIcon(heroImg = hero.imageResourceId)
        }

    }


}

@Composable
fun SuperHeroIcon(
    @DrawableRes heroImg: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier
            .size(72.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        Image(
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter,
            painter = painterResource(heroImg),
            contentDescription = null
        )
    }

}

@Composable
fun SuperHeroInfo(
    modifier: Modifier = Modifier,
    @StringRes name: Int,
    @StringRes desc: Int,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(name),
            style = MaterialTheme.typography.displaySmall,
        )
        Text(
            text = stringResource(desc),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}