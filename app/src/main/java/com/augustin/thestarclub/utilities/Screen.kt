package com.augustin.thestarclub.utilities

sealed class Screen(val route: String)
{
    object  MainScreen : Screen(route = "main_screen")
    object  DetailScreen : Screen(route = "detail_screen")
    object  CasinoDollarsScreen : Screen(route = "casino_dollars_screen")
    object  GiftsScreen : Screen(route = "gifts_screen")
    object  UserTierPointScreen : Screen(route = "user_tier_screen")

    fun withArgs(vararg args: String): String{
        return buildString {
            append(route)
            args.forEach {arg ->
                append("/$arg")
            }
        }
    }
}