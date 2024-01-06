package com.example.test_in_kotlin.data.transactions.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
class TransactionEntity (
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0,
    val dienst : String,
    var project : String,
    var beschrijving : String,
    var kilometers : String,
    var factureerbaar : Boolean,
    var tijdsduur : String,
    var teControleren : Boolean,
    var date : String,

    )