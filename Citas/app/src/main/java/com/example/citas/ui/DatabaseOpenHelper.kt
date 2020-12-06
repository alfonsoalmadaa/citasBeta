package com.example.citas.ui

import android.content.Context
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper


class DatabaseOpenHelper(context: Context?) :
    SQLiteAssetHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_NAME = "example_sites.db"
        private const val DATABASE_VERSION = 1
    }
}