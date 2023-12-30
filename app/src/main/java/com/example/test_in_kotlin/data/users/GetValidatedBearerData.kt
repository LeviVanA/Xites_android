package com.example.test_in_kotlin.data.users

import kotlinx.serialization.Serializable

@Serializable
data class ValidatedBearer(
    val token: String,
    val validated : Boolean
)
