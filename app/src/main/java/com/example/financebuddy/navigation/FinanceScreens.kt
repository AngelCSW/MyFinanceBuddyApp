package com.example.financebuddy.navigation

import java.lang.IllegalArgumentException

//www.google.com/sign_in
enum class FinanceScreens {
    HomeScreen,
    Dashboard,
    SavingsScreen,
    TravelsScreen;
    companion object {
        fun fromRoute(route: String?): FinanceScreens
                = when (route?.substringBefore("/")) {
            HomeScreen.name -> HomeScreen
            Dashboard.name -> Dashboard
            SavingsScreen.name -> SavingsScreen
            TravelsScreen.name -> TravelsScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}