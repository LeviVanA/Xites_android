package com.example.test_in_kotlin.screens.Registration

import androidx.compose.ui.text.input.TextFieldValue

data class RegistrationUIState(
    val date: String = "",
    val project: String = "Project",
    val dienst: String = "Dienst",
    val factureerbaar: Boolean = false,
    val teControleren: Boolean = false,
    val tijdsduur: String = "",
    val kilometers: String = "",
    val beschrijving: String = "",
    )