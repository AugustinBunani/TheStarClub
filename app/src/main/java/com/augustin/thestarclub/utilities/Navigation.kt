package com.augustin.thestarclub

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.augustin.thestarclub.utilities.Screen
import com.augustin.thestarclub.view.*

@Composable
fun Navigator() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(
            route = Screen.DetailScreen.route + "/{name}",

            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "GUS"
                    nullable = true
                }
            )
        ) {
            DetailScreen()
        }

        composable(
            route = Screen.CasinoDollarsScreen.route + "/{name}",

            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "GUS"
                    nullable = true
                }
            )
        ) {
            CasinoDollarsScreen()
        }

        composable(
            route = Screen.GiftsScreen.route + "/{name}",

            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "GUS"
                    nullable = true
                }
            )
        ) {
            GiftsScreen()
        }

        composable(
            route = Screen.UserTierPointScreen.route + "/{name}",

            arguments = listOf(
                navArgument("name") {
                    type = NavType.StringType
                    defaultValue = "GUS"
                    nullable = true
                }
            )
        ) {
            UserTierPointScreen()
        }

    }
}

@Composable
fun MainScreen(navController: NavController) {

    UserDataView(navController)
}

@Composable
fun DetailScreen() {
    UserBenefitsView()

}

@Composable
fun CasinoDollarsScreen() {
    UserCasinoDollarsView()

}

@Composable
fun GiftsScreen() {
    UserGiftsView()

}
@Composable
fun UserTierPointScreen() {
    UserTierPointsView()

}
