package com.example.test_in_kotlin.screens.Registrations

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test_in_kotlin.components.CustomTextField
import com.example.test_in_kotlin.components.TopB
import com.example.test_in_kotlin.data.transactions.Item

@Composable
fun Registrations( modifier: Modifier, toHome: () -> Unit){

    //val registrationsList by registrationsViewModel.registrations.observeAsState(emptyList())
    //val sendUiState = registrationsViewModel.registrations.collectAsState()
    Column{
        TopB()
        IconButton(
            onClick = { toHome()}
        ) {
            Icon(
                Icons.Outlined.ArrowBack,
                contentDescription = "Back arrow",
            )
        }
        Table(modifier)
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}
@Composable
fun Table(modifier:Modifier, registrationsViewModel: RegistrationsViewModel = viewModel(factory = RegistrationsViewModel.Factory)) {
    var searchByUser by remember { mutableStateOf("") }
    var registrationsList by remember { mutableStateOf<List<Item>?>(null) }

    // Use LaunchedEffect to trigger the fetchRegistrations
    LaunchedEffect(true) {
        registrationsViewModel.fetchRegistrations()
    }

    // Observe the registrations state using remember
    // This will automatically recompose when the registrations state changes
    registrationsList = remember(registrationsViewModel.registrations.value) {
        registrationsViewModel.registrations.value
    }

    // Log information about registrationsList
    Log.d("Table", "Registrations List: $registrationsList")

    DisposableEffect(true) {
        // This block will be executed when the composable is first created

        // Clean-up block, not strictly necessary in this case
        onDispose {
            // Any clean-up code goes here
        }
    }

    val column1Weight = .5f // 30%
    val column2Weight = .7f // 70%
    val column3Weight = .5f

    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(16.dp)) {
        val tableData = registrationsList?.map { item ->
            Triple(item.date, item.user, item.project)
        }
        Log.d("Table", "table data: $tableData")
        item {
            // Search bar
            Row {

                CustomTextField(
                    modifier = modifier,
                    label = "filter op user",
                    onValueChange = { searchByUser = it },
                    value = searchByUser,
                    onTrailingIconClick = { "" },
                    width = 175.dp
                )
            }
        }
        item {
            Row(Modifier.background(Color.Gray)) {
                TableCell(text = "date", weight = column1Weight)
                TableCell(text = "user", weight = column2Weight)
                TableCell(text = "project", weight = column3Weight)
            }
        }
        if (tableData != null) {
            items(tableData.filter { it.second.contains(searchByUser, ignoreCase = true) }) {
                val (id, text, bedrijf) = it
                Row(Modifier.fillMaxWidth()) {
                    TableCell(text = id, weight = column1Weight)
                    TableCell(text = text, weight = column2Weight)
                    TableCell(text = bedrijf, weight = column3Weight)
                }
            }
        }
    }
}

