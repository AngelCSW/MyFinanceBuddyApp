package com.example.financebuddy.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financebuddy.R
import com.example.financebuddy.SavingViewModel
import com.example.financebuddy.TransactionViewModel
import com.example.financebuddy.TravelViewModel
import com.example.financebuddy.data.Saving
import com.example.financebuddy.data.Transaction
import com.example.financebuddy.data.Travel
import com.example.financebuddy.widgets.AppBarView
import kotlinx.coroutines.launch

@Composable
fun AddUpdateTransactionView(
    id: Long,
    viewModel: TransactionViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    //val scaffoldState = rememberScaffoldState()

    var titleError by remember { mutableStateOf(false) }
    var typeError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    // Initialize transaction state
    if (id != 0L) {
        val transaction = viewModel.getATransactionById(id).collectAsState(initial = Transaction(0L, "", "", 0.00, ""))
        viewModel.transactionTitleState = transaction.value.title
        viewModel.transactionTypeState = transaction.value.type
        viewModel.transactionAmountState = transaction.value.amount.toString()
        viewModel.transactionDescriptionState = transaction.value.description
    } else {
        viewModel.transactionTitleState = ""
        viewModel.transactionTypeState = ""
        viewModel.transactionAmountState = ""
        viewModel.transactionDescriptionState = ""

    }

    Scaffold(
        topBar = {
            AppBarView(
                title = if (id != 0L) stringResource(id = R.string.update_transaction) else stringResource(id = R.string.add_transaction)
            ) { navController.navigateUp() }
        },
        //scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Title",
                value = viewModel.transactionTitleState,
                onValueChanged = {
                    viewModel.onTransactionTitleChanged(it)
                    titleError = false
                },
                isError = titleError,
                errorMessage = if (titleError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionDropDown(
                options = listOf("Income", "Expense"),
                selectedOption = viewModel.transactionTypeState,
                onOptionSelected = {
                    viewModel.onTransactionTypeChanged(it)
                    typeError = false
                },
                isError = typeError,
                errorMessage = if (typeError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Amount(RM)",
                value = viewModel.transactionAmountState,
                onValueChanged = {
                    viewModel.onTransactionAmountChanged(it)
                    amountError = false
                },
                isError = amountError,
                errorMessage = if (amountError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Description",
                value = viewModel.transactionDescriptionState,
                onValueChanged = { viewModel.onTransactionDescriptionChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    titleError = viewModel.transactionTitleState.isEmpty()
                    typeError = viewModel.transactionTypeState.isEmpty()
                    amountError = viewModel.transactionAmountState.isEmpty()

                    if (!titleError && !typeError && !amountError) {
                        if (id != 0L) {
                            // UpdateTransaction
                            viewModel.updateTransaction(
                                Transaction(
                                    id = id,
                                    title = viewModel.transactionTitleState.trim(),
                                    type = viewModel.transactionTypeState.trim(),
                                    amount = viewModel.transactionAmountState.toDoubleOrNull() ?: 0.0,
                                    description = viewModel.transactionDescriptionState.trim()
                                )
                            )
                        } else {
                            // AddTransaction
                            viewModel.addTransaction(
                                Transaction(
                                    title = viewModel.transactionTitleState.trim(),
                                    type = viewModel.transactionTypeState.trim(),
                                    amount = viewModel.transactionAmountState.toDoubleOrNull() ?: 0.0,
                                    description = viewModel.transactionDescriptionState.trim()
                                )
                            )
                        }
                        snackMessage.value = if (id != 0L) "Transaction updated successfully" else "Transaction added successfully"
                        scope.launch {
                            navController.navigateUp()
                        }
                    } else {
                        snackMessage.value = "Please fill in all required fields"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_transaction) else stringResource(id = R.string.add_transaction),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            if (snackMessage.value.isNotEmpty()) {
                Snackbar(
                    action = {
                        TextButton(onClick = { snackMessage.value = "" }) {
                            Text("Dismiss")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(snackMessage.value)
                }
            }
        }
    }
}

@Composable
fun AddUpdateSavingView(
    id: Long,
    viewModel: SavingViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    //val scaffoldState = rememberScaffoldState()

    var titleError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    // Initialize saving state
    if (id != 0L) {
        val saving = viewModel.getASavingById(id).collectAsState(initial = Saving(0L, "", 0.00, ""))
        viewModel.savingTitleState = saving.value.title
        viewModel.savingAmountState = saving.value.amount.toString()
        viewModel.savingDescriptionState = saving.value.description
    } else {
        viewModel.savingTitleState = ""
        viewModel.savingAmountState = ""
        viewModel.savingDescriptionState = ""

    }

    Scaffold(
        topBar = {
            AppBarView(
                title = if (id != 0L) stringResource(id = R.string.update_saving) else stringResource(id = R.string.add_saving)
            ) { navController.navigateUp() }
        },
        //scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Title",
                value = viewModel.savingTitleState,
                onValueChanged = {
                    viewModel.onSavingTitleChanged(it)
                    titleError = false
                },
                isError = titleError,
                errorMessage = if (titleError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Amount(RM)",
                value = viewModel.savingAmountState,
                onValueChanged = {
                    viewModel.onSavingAmountChanged(it)
                    amountError = false
                },
                isError = amountError,
                errorMessage = if (amountError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Description",
                value = viewModel.savingDescriptionState,
                onValueChanged = { viewModel.onSavingDescriptionChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    titleError = viewModel.savingTitleState.isEmpty()
                    amountError = viewModel.savingAmountState.isEmpty()

                    if (!titleError && !amountError) {
                        if (id != 0L) {
                            // UpdateTransaction
                            viewModel.updateSaving(
                                Saving(
                                    id = id,
                                    title = viewModel.savingTitleState.trim(),
                                    amount = viewModel.savingAmountState.toDoubleOrNull() ?: 0.0,
                                    description = viewModel.savingDescriptionState.trim()
                                )
                            )
                        } else {
                            // AddTransaction
                            viewModel.addSaving(
                                Saving(
                                    title = viewModel.savingTitleState.trim(),
                                    amount = viewModel.savingAmountState.toDoubleOrNull() ?: 0.0,
                                    description = viewModel.savingDescriptionState.trim()
                                )
                            )
                        }
                        snackMessage.value = if (id != 0L) "Saving updated successfully" else "Saving added successfully"
                        scope.launch {
                            navController.navigateUp()
                        }
                    } else {
                        snackMessage.value = "Please fill in all required fields"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_saving) else stringResource(id = R.string.add_saving),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
            if (snackMessage.value.isNotEmpty()) {
                Snackbar(
                    action = {
                        TextButton(onClick = { snackMessage.value = "" }) {
                            Text("Dismiss")
                        }
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(snackMessage.value)
                }
            }
        }
    }
}

@Composable
fun AddUpdateTravelView(
    id: Long,
    viewModel: TravelViewModel,
    navController: NavController
) {
    val snackMessage = remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    //val scaffoldState = rememberScaffoldState()

    var titleError by remember { mutableStateOf(false) }
    var typeError by remember { mutableStateOf(false) }
    var amountError by remember { mutableStateOf(false) }

    // Initialize transaction state
    if (id != 0L) {
        val travel = viewModel.getATravelTransactionById(id).collectAsState(initial = Travel(0L, "", "", 0.00, ""))
        viewModel.travelTitleState = travel.value.title
        viewModel.travelTypeState = travel.value.type
        viewModel.travelAmountState = travel.value.amount.toString()
        viewModel.travelDescriptionState = travel.value.description
    } else {
        viewModel.travelTitleState = ""
        viewModel.travelTypeState = ""
        viewModel.travelAmountState = ""
        viewModel.travelDescriptionState = ""

    }

    Scaffold(
        topBar = {
            AppBarView(
                title = if (id != 0L) stringResource(id = R.string.update_travel) else stringResource(id = R.string.add_travel)
            ) { navController.navigateUp() }
        },
        //scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .wrapContentSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Title",
                value = viewModel.travelTitleState,
                onValueChanged = {
                    viewModel.onTravelTransactionTitleChanged(it)
                    titleError = false
                },
                isError = titleError,
                errorMessage = if (titleError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionDropDown(
                options = listOf("Saving", "Expense"),
                selectedOption = viewModel.travelTypeState,
                onOptionSelected = {
                    viewModel.onTravelTransactionTypeChanged(it)
                    typeError = false
                },
                isError = typeError,
                errorMessage = if (typeError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Amount(RM)",
                value = viewModel.travelAmountState,
                onValueChanged = {
                    viewModel.onTravelTransactionAmountChanged(it)
                    amountError = false
                },
                isError = amountError,
                errorMessage = if (amountError) "This is a required field." else null
            )

            Spacer(modifier = Modifier.height(10.dp))

            TransactionTextField(
                label = "Description",
                value = viewModel.travelDescriptionState,
                onValueChanged = { viewModel.onTravelTransactionDescriptionChanged(it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    titleError = viewModel.travelTitleState.isEmpty()
                    typeError = viewModel.travelTypeState.isEmpty()
                    amountError = viewModel.travelAmountState.isEmpty()

                    if (!titleError && !typeError && !amountError) {
                        if (id != 0L) {
                            // UpdateTransaction
                            viewModel.updateTravelTransaction(
                                Travel(
                                    id = id,
                                    title = viewModel.travelTitleState.trim(),
                                    type = viewModel.travelTypeState.trim(),
                                    amount = viewModel.travelAmountState.toDoubleOrNull() ?: 0.0,
                                    description = viewModel.travelDescriptionState.trim()
                                )
                            )
                        } else {
                            // AddTransaction
                            viewModel.addTravelTransaction(
                                Travel(
                                    title = viewModel.travelTitleState.trim(),
                                    type = viewModel.travelTypeState.trim(),
                                    amount = viewModel.travelAmountState.toDoubleOrNull() ?: 0.0,
                                    description = viewModel.travelDescriptionState.trim()
                                )
                            )
                        }
                        snackMessage.value = if (id != 0L) "Transaction updated successfully" else "Transaction added successfully"
                        scope.launch {
                            navController.navigateUp()
                        }
                    } else {
                        snackMessage.value = "Please fill in all required fields"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (id != 0L) stringResource(id = R.string.update_travel) else stringResource(id = R.string.add_travel),
                    style = TextStyle(fontSize = 18.sp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = Color.Black,
            unfocusedBorderColor = Color.Black,
            cursorColor = Color.Black,
            focusedLabelColor = Color.Black,
            unfocusedLabelColor = Color.Black
        ),
        isError = isError
    )
    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}

// Drop Down Menu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDropDown(
    options: List<String>,
    selectedOption: String,
    onOptionSelected: (String) -> Unit,
    isError: Boolean = false,
    errorMessage: String? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            modifier = Modifier.menuAnchor().fillMaxWidth(),
            readOnly = true,
            value = selectedOption,
            onValueChange = {},
            label = { Text("Type") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                focusedContainerColor = if (isError) Color.Red.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = if (isError) Color.Red.copy(alpha = 0.1f) else Color.LightGray
            ),
            isError = isError
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        onOptionSelected(selectionOption)
                        expanded = false
                    },
                )
            }
        }
    }
    if (isError && errorMessage != null) {
        Text(
            text = errorMessage,
            color = Color.Red,
            style = TextStyle(fontSize = 12.sp),
            modifier = Modifier.padding(start = 16.dp, top = 4.dp)
        )
    }
}


@Preview
@Composable
fun TransactionTextFieldPrev(){
    TransactionTextField(label = "text", value = "text", onValueChanged = {})
}