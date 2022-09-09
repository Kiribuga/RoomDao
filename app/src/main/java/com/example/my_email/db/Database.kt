package com.example.my_email.db

import android.content.Context
import androidx.room.Room

object Database {

    lateinit var instance: EmailDatabase
        private set

    fun init(context: Context) {
        instance = Room.databaseBuilder(
            context,
            EmailDatabase::class.java,
            EmailDatabase.DB_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}