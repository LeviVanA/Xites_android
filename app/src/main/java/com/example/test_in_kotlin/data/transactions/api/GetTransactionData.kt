package com.example.test_in_kotlin.data.transactions.api

data class Item(
    val id: Int,
    val date: String,
    val user: String,
    val project: String
)

data class TranactionData(
    val items: List<Item>
)