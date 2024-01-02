package com.example.test_in_kotlin.data.users


data class ValidatedBearer(
    val token: String="",
    val validated : Boolean=true
)
