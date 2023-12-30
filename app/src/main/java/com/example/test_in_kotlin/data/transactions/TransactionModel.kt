package com.example.test_in_kotlin.data.transactions

data class TransactionModel (
    val dienst : String,
    val project : String,
    val beschrijving : String,
    val kilometers : String,
    val factureerbaar : Boolean,
    val tijdsduur : String,
    val teControleren : Boolean,
    val date : String
)