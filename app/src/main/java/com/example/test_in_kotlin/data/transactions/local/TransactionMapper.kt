package com.example.test_in_kotlin.data.transactions.local

import com.example.test_in_kotlin.data.transactions.GetTransaction
import com.example.test_in_kotlin.data.transactions.TransactionModel

fun entityToModelTransaction(entity: TransactionEntity): TransactionModel {
    return TransactionModel(
        entity.dienst,
        entity.project,
        entity.beschrijving,
        entity.kilometers,
        entity.factureerbaar,
        entity.tijdsduur,
        entity.teControleren,
        entity.date
    )
}