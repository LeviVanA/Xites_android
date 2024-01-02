package com.example.test_in_kotlin.data.transactions.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class TransactionsDatabase : RoomDatabase(){
    abstract val transactionsDbDao: TransactionDatabaseDao

    companion object {

        @Volatile
        private var Instance: TransactionsDatabase? = null


        fun getDatabase(context: Context): TransactionsDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, TransactionsDatabase::class.java, "item_database")
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }

        }
    }

}