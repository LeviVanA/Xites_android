package com.example.test_in_kotlin.screens.Registration


import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.test_in_kotlin.components.DatePick
import com.example.test_in_kotlin.components.ExposedDMBox
import com.example.test_in_kotlin.components.TopB
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.test_in_kotlin.components.Buttons
import com.example.test_in_kotlin.components.CustomTextField
import com.example.test_in_kotlin.data.transactions.api.Items


@SuppressLint("SuspiciousIndentation")
@Composable
fun RegistrationScreen(toHome: () -> Unit, modifier: Modifier, registrationViewModel: RegistrationViewModel = viewModel(factory = RegistrationViewModel.Factory)){
    val RegistrationUIState = registrationViewModel.uiState.collectAsState()
    var projectsList by remember { mutableStateOf<List<Items.ProjectItem>?>(null) }
    var dienstenList by remember { mutableStateOf<List<Items.DienstItem>?>(null) }

    val ctx = LocalContext.current

    registrationViewModel.cacheCheck(ctx)
    LaunchedEffect(true) {
        registrationViewModel.fetchDienstenProjects()
    }
    projectsList = registrationViewModel.projects.value
    dienstenList = registrationViewModel.diensten.value
    println(projectsList)
    println(dienstenList)

    Column(){
        TopB()
        IconButton(
            onClick = { toHome()}
        ) {
            Icon(
                Icons.Outlined.ArrowBack,
                contentDescription = "Back arrow",
            )
        }
        DatePick(modifier = modifier.align(Alignment.CenterHorizontally),
            onValueChange = {updatedValue ->
            val updatedUiState = RegistrationUIState.value.copy(date = updatedValue)
                registrationViewModel.updateUiState(updatedUiState)},
            value = RegistrationUIState.value.date)
        Row(modifier = modifier.align(Alignment.CenterHorizontally)) {
            ExposedDMBox(
                exVarP = projectsList,
                modifier = Modifier,
                onValueChange = { updatedValue ->
                    val updatedUiState = RegistrationUIState.value.copy(project = updatedValue)
                    registrationViewModel.updateUiState(updatedUiState)},
                value = RegistrationUIState.value.project)
            ExposedDMBox(
                exVarD = dienstenList,
                modifier = Modifier,
                onValueChange = { updatedValue ->
                    val updatedUiState = RegistrationUIState.value.copy(dienst = updatedValue)
                    registrationViewModel.updateUiState(updatedUiState)},
                value = RegistrationUIState.value.dienst
                )
        }
        Row(modifier = modifier.align(Alignment.Start).padding(30.dp,0.dp,0.dp,0.dp)) {
            Column {
                Text(text = "Factureerbaar",
                    modifier.requiredWidth(width = 145.dp))
                SwitchMin(onCheckedChange = {updatedValue ->
                    val updatedUiState = RegistrationUIState.value.copy(factureerbaar = updatedValue)
                    registrationViewModel.updateUiState(updatedUiState)},checked = RegistrationUIState.value.factureerbaar)
            }
        }
        Row (modifier = modifier.align(Alignment.CenterHorizontally))
        {
            CustomTextField(
                modifier = modifier.padding(15.dp, 0.dp),
                label = "Tijdsduur",
                onValueChange = { updatedValue ->
                    val updatedUiState = RegistrationUIState.value.copy(tijdsduur = updatedValue)
                    registrationViewModel.updateUiState(updatedUiState)},
                value = RegistrationUIState.value.tijdsduur,
                onTrailingIconClick = { "" },
                width = 175.dp)
            CustomTextField(
                modifier = modifier.padding(15.dp, 0.dp),
                label = "gereden kilometers",
                onValueChange = { updatedValue ->
                    val updatedUiState = RegistrationUIState.value.copy(kilometers = updatedValue)
                    registrationViewModel.updateUiState(updatedUiState)},
                value = RegistrationUIState.value.kilometers,
                onTrailingIconClick = { registrationViewModel.updateUiState(RegistrationUIState.value.copy(kilometers = "")) },
                width = 175.dp)
        }
        Textarea(
            onValueChange = { updatedValue ->
            val updatedUiState = RegistrationUIState.value.copy(beschrijving = updatedValue)
            registrationViewModel.updateUiState(updatedUiState)},
            value = RegistrationUIState.value.beschrijving)
        BasicCheckbox(modifier = modifier,onCheckedChange = {updatedValue ->
            val updatedUiState = RegistrationUIState.value.copy(teControleren = updatedValue)
            registrationViewModel.updateUiState(updatedUiState)},checked = RegistrationUIState.value.teControleren)
        Row {
            Buttons(text = "Opslaan", onClick = { registrationViewModel.sendLoad(toHome, ctx) })
            val errorMessage = registrationViewModel.errorState.collectAsState().value
            errorMessage?.let {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    action = {
                        TextButton(onClick = { registrationViewModel.updateErrorState(null) }) {
                            Text("Dismiss")
                        }
                    }


                ) {
                    Text(it)
                }
            }
        }

    }
}

@Composable
fun SwitchMin(onCheckedChange:(Boolean) -> Unit, checked:Boolean) {
    Switch(
        checked = checked,
        onCheckedChange = onCheckedChange
    )
}
@Composable
fun BasicCheckbox(modifier: Modifier, onCheckedChange:(Boolean) -> Unit, checked:Boolean) {
    val checked = remember { mutableStateOf(false) }

    Row (){
        Checkbox(
            checked = checked.value,
            onCheckedChange = { isChecked -> checked.value = isChecked }
        )
        Text(text = "te controleren", modifier.align(Alignment.CenterVertically))
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Textarea(onValueChange: (String) -> Unit, value: String,) {
    val text = rememberSaveable { mutableStateOf("") }
    TextField(
        value = value,
        placeholder = { Text(text = "beschrijving")},
        onValueChange = onValueChange, modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(8.dp))
    )
}
