package com.example.restaurantapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

@Composable
fun RestaurantsScreen(onItemClick: (id: Int) -> Unit = {} ) {
    val viewModel: RestaurantsViewModel = viewModel()
    LazyColumn(
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 8.dp
        )
    ) {
        items(viewModel.state.value) { restaurant ->
            RestaurantItem(restaurant,
                onFavouriteClick = { id -> viewModel.toggleFavourite(id) },
                onItemClick = { id -> onItemClick(id) })
        }
    }
}

@Composable
fun RestaurantItem(item: Restaurant, onFavouriteClick: (id: Int) -> Unit, onItemClick: (id: Int) -> Unit) {
    val favouriteState = remember { mutableStateOf(false) }
    val icon = if (item.isFavourite)
        Icons.Filled.Favorite
    else
        Icons.Filled.FavoriteBorder
    Card(elevation = 4.dp,
        modifier = Modifier.padding(8.dp).clickable { onItemClick(item.id) }
    ) {
        Row(verticalAlignment =
        Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)) {
            RestaurantIcon(
                Icons.Filled.Place,
                Modifier.weight(0.15f))
            RestaurantDetails(item.title, item.description, Modifier.weight(0.85f))
            RestaurantIcon(icon, Modifier.weight(0.15f)) {
                onFavouriteClick(item.id)
            }
        }
    }
}

@Composable
private fun FavouriteIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit) {
    Image(
        imageVector = icon,
        contentDescription = "Favourite restaurant icon",
        modifier = modifier.padding(8.dp).clickable { onClick() }
    )
}

@Composable
fun RestaurantIcon(icon: ImageVector, modifier: Modifier, onClick: () -> Unit = { }) {
    Image(imageVector = icon,
        contentDescription = "Restaurant icon",
        modifier = modifier.padding(8.dp).clickable{ onClick() }
    )
}

@Composable
fun RestaurantDetails(title: String, description: String, modifier: Modifier,
                      horizontalAlignment: Alignment.Horizontal = Alignment.Start) {
    Column(modifier = modifier, horizontalAlignment = horizontalAlignment) {
        Text(text = title,
            style = MaterialTheme.typography.h6)
        CompositionLocalProvider(
            LocalContentAlpha provides
                    ContentAlpha.medium) {
            Text(text = description,
                style = MaterialTheme.typography.body2)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    RestaurantAppTheme {
        RestaurantsScreen()
    }
}