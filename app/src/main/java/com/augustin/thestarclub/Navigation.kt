package com.augustin.thestarclub

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.augustin.thestarclub.view.UserBenefitsView
import com.augustin.thestarclub.view.UserDataView

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