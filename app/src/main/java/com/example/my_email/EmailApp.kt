package com.example.my_email

import android.app.Application
import com.example.my_email.db.Database

class EmailApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Database.init(this)
    }

}