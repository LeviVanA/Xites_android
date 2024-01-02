package com.example.test_in_kotlin.data.transactions.api

sealed class Items {
    data class ProjectItem(
        val id: Int,
        val naam: String
    )
    data class DienstItem(
        val id: Int,
        val naam: String
    )
}

data class DienstData(
    val items: List<Items.DienstItem>
)


data class ProjectData(
    val items: List<Items.ProjectItem>
)