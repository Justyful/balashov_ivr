package com.example.physicsfinal

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class PhysicsFINAL : Application() {
    var db : DataBase? = null
    override fun onCreate() {
        super.onCreate()
        db = DataBase(this)
        db!!.hasVisited = false
        db!!.START()
    }
    fun getDB() : DataBase? {
        return db
    }
}