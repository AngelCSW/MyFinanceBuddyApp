package com.example.financebuddy.screens.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financebuddy.navigation.Screen
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import com.example.financebuddy.TravelViewModel
import com.example.financebuddy.data.Travel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TravelsScreen(navController: NavController, viewModel: TravelViewModel) {
    val travel = viewModel.getAllTravelTransactions.collectAsState(initial = listOf())
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navDrawerItem = listOf(
        Screen.Dashboard,
        Screen.SavingsScreen,
        Screen.TravelsScreen
    )

    // Compute total income, total expenses, and balance
    val totalSavings = travel.value.filter { it.type == "Saving" }.sumOf { it.amount }
    val totalExpenses = travel.value.filter { it.type == "Expense" }.sumOf { it.amount }
    val totaltravel = totalSavings - totalExpenses

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Header - Title
                    Text(
                        text = "Menu",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.W500,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )

                    // Navigation Items
                    navDrawerItem.forEachIndexed { index, screen ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = screen.title,
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.W400
                                )
                            },
                            selected = false,
                            onClick = {
                                scope.launch { drawerState.close() }
                                navController.navigate(screen.route) {
                                    // Using a single navigation option to ensure correct behavior
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Travel Transactions", fontSize = 30.sp, color = Color.Black) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Blue),
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Filled.Menu, contentDescription = "Open Drawer")
                        }
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.AddTravelScreen.route + "/0L")
                    },
                    containerColor = Color.Blue,
                    contentColor = Color.White
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Travel")
                }
            },
            content = { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.Start
                ) {
                    Text("TOTAL TRAVEL BALANCE", fontSize = 30.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("RM ${String.format("%.2f", totaltravel)}", fontSize = 50.sp, fontWeight = FontWeight.ExtraBold)

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp)
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding()
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("RM ${String.format("%.2f", totalSavings)}", fontSize = 20.sp, color = Color.Green, fontWeight = FontWeight.Bold)

                                Spacer(modifier = Modifier.height(10.dp))

                                Text("SAVING", fontSize = 15.sp)
                            }

                            Spacer(modifier = Modifier.width(16.dp))

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(16.dp),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("RM ${String.format("%.2f", totalExpenses)}", fontSize = 20.sp, color = Color.Red, fontWeight = FontWeight.Bold)

                                Spacer(modifier = Modifier.height(10.dp))

                                Text("EXPENSE", fontSize = 15.sp)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("TRANSACTIONS", fontSize = 24.sp)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Display lists of available transactions with swipe-to-delete
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        items(
                            items = travel.value,
                            key = { travel -> travel.id }
                        ) { travel ->
                            val dismissState = rememberSwipeToDismissBoxState(
                                confirmValueChange = { dismissValue ->
                                    if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                                        viewModel.deleteTravelTransaction(travel)
                                        true
                                    } else {
                                        false
                                    }
                                }
                            )
                            SwipeToDismissBox(
                                state = dismissState,
                                backgroundContent = {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .background(Color.Red)
                                            .padding(horizontal = 20.dp),
                                        contentAlignment = Alignment.CenterEnd
                                    ) {
                                        Icon(
                                            Icons.Default.Delete,
                                            contentDescription = "Delete",
                                            tint = Color.White
                                        )
                                    }
                                },
                                content = {
                                    TravelItem(travel = travel) {
                                        val id = travel.id
                                        navController.navigate(Screen.AddTravelScreen.route + "/$id")
                                    }
                                },
                                enableDismissFromStartToEnd = false,
                                enableDismissFromEndToStart = true
                            )
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun TravelItem(travel: Travel, onClick: () -> Unit) {
    val (icon, color) = when (travel.type) {
        "Saving" -> Pair("+", Color.Green)
        "Expense" -> Pair("-", Color.Red)
        else -> Pair("", Color.Black)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = travel.title, fontWeight = FontWeight.ExtraBold)

            Spacer(modifier = Modifier.width(110.dp))

            Text(text = icon, fontWeight = FontWeight.Bold, color = color)

            Spacer(modifier = Modifier.width(8.dp))

            Text(text = "RM ${String.format("%.2f", travel.amount)}", fontWeight = FontWeight.Bold, color = color)
        }
    }
}

