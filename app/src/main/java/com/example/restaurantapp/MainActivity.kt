package com.example.restaurantapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.restaurantapp.ui.theme.RestaurantAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppTheme {
                restaurantsApp()
            }
        }
    }

    @Composable
    private fun restaurantsApp() {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "restaurants") {
            composable("restaurants") {
                RestaurantsScreen { id ->
                    navController.navigate("restaurants/$id")
                }
            }
            composable(
                "restaurants/{restaurant_id}",
                arguments = listOf(navArgument("restaurant_id") {
                    type = NavType.IntType
                    }),
                deepLinks = listOf(navDeepLink {
                    uriPattern = "www.restaurantsapp.details.com/{restaurant_id}"
                })
            ) { RestaurantDetailScreen() }
        }
    }
}
