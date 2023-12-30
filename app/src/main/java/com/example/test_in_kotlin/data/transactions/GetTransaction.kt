package com.example.test_in_kotlin.data.transactions

data class GetTransaction (
    val id : Int,
    val date : String,
    val dienstId : Int,
    val projectId : Int,
    val beschrijving: String,
    val kilometers : String,
    val factureerbaar : Boolean,
    val tijdsduur : String,
    val teControlern : String
)