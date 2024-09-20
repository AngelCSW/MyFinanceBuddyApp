package com.example.financebuddy.navigation

sealed class Screen(
    val title: String,
    val route:String
) {
    object Dashboard: Screen(
        title = "Dashboard",
        route = "dashboard"
    )

    object AddTransactionScreen: Screen(
        title = "Add Transaction",
        route = "add_transaction_screen"
    )

    object SavingsScreen: Screen(
        title = "My Savings",
        route = "savings_screen"
    )

    object AddSavingScreen: Screen(
        title = "Add Saving Transaction Screen",
        route = "add_saving_screen"
    )

    object TravelsScreen: Screen(
        title = "Travel",
        route = "travels_screen"
    )

    object AddTravelScreen: Screen(
        title = "Add Travel Transaction Screen",
        route = "add_travel_screen"
    )
}