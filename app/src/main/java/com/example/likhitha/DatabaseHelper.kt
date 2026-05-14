package com.example.likhitha

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "WorkersDB", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(
            "CREATE TABLE workers (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT," +
                    "worktype TEXT," +
                    "phone TEXT," +
                    "location TEXT," +
                    "experience TEXT," +
                    "available INTEGER)"
        )
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {

        db.execSQL("DROP TABLE IF EXISTS workers")
        onCreate(db)
    }

    fun insertWorker(
        name: String,
        workType: String,
        phone: String,
        location: String,
        experience: String,
        available: Int
    ): Boolean {

        val db = this.writableDatabase

        val values = ContentValues()

        values.put("name", name)
        values.put("worktype", workType)
        values.put("phone", phone)
        values.put("location", location)
        values.put("experience", experience)
        values.put("available", available)

        val result = db.insert("workers", null, values)

        db.close()

        return result != -1L
    }
}