package com.example.financebuddy.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.financebuddy.TransactionViewModel
import com.example.financebuddy.SavingViewModel // Make sure this import matches your actual package
import com.example.financebuddy.screens.home.HomeScreen
import com.example.financebuddy.screens.details.Dashboard
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.financebuddy.TravelViewModel
import com.example.financebuddy.screens.details.AddUpdateSavingView
import com.example.financebuddy.screens.details.AddUpdateTransactionView
import com.example.financebuddy.screens.details.AddUpdateTravelView
import com.example.financebuddy.screens.details.SavingsScreen
import com.example.financebuddy.screens.details.TravelsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation(
    transactionViewModel: TransactionViewModel = viewModel(),
    savingViewModel: SavingViewModel = viewModel(),
    travelViewModel: TravelViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    NavHost(navController = navController, startDestination = FinanceScreens.HomeScreen.name) {

        composable(FinanceScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        composable(Screen.Dashboard.route) {
            Dashboard(navController, transactionViewModel)
        }

        composable(Screen.AddTransactionScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            AddUpdateTransactionView(id = id, viewModel = transactionViewModel, navController = navController)
        }

        composable(Screen.SavingsScreen.route) {
            SavingsScreen(navController, savingViewModel)
        }

        composable(Screen.AddSavingScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            AddUpdateSavingView(id = id, viewModel = savingViewModel, navController = navController)
        }

        composable(Screen.TravelsScreen.route) {
            TravelsScreen(navController, travelViewModel)
        }

        composable(Screen.AddTravelScreen.route + "/{id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.LongType
                    defaultValue = 0L
                    nullable = false
                }
            )
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            AddUpdateTravelView(id = id, viewModel = travelViewModel, navController = navController)
        }
    }
}
