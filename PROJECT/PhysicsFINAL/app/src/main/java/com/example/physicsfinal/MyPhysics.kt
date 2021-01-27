package com.example.physicsfinal

import android.app.Application
import com.example.physicsfinal.Guide.DataBase

class MyPhysics : Application() {
    var db : DataBase? = null
    override fun onCreate() {
        super.onCreate()
        db = DataBase(this)
        db!!.launchDB()
    }
    fun getDB() : DataBase? {
        return db
    }
}