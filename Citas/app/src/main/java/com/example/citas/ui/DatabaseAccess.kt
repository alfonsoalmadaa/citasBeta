package com.example.citas.ui

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class DatabaseAccess private constructor(context: Context) {
    private val openHelper: SQLiteOpenHelper
    private var database: SQLiteDatabase? = null


    /**
     * Open the database connection.
     */
    fun open() {
        database = openHelper.writableDatabase
    }

    /**
     * Close the database connection.
     */
    fun close() {
        if (database != null) {
            database!!.close()
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    val addresses: List<String>
        get() {
            val list: MutableList<String> = ArrayList()
            val cursor = database!!.rawQuery("SELECT address FROM sites", null)
            cursor.moveToFirst()
            while (!cursor.isAfterLast) {
                list.add(cursor.getString(0))
                cursor.moveToNext()
            }
            cursor.close()
            return list
        }

    fun makeSearch(local_name: String) : MutableList<String> {
        val locales: MutableList<String> = ArrayList()
        val cursor = if (local_name.isNotBlank() || local_name.isNotEmpty()){
            database!!.rawQuery("SELECT id,address FROM sites WHERE LOWER(name) LIKE LOWER('%$local_name%')", null)
        } else {
            database!!.rawQuery("SELECT id,address FROM sites", null)
        }
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            locales.add(cursor.getString(0))
            locales.add(cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return locales
    }

    fun getNotifications(user: String, days: Int) : MutableList<String> {
        val citas: MutableList<String> = ArrayList()

        val cursor = database!!.rawQuery("SELECT id_user,id_site,hora_cita,fecha_cita FROM citas", null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            if(cursor.getString(0).equals(user)){
                val app_date = Calendar.getInstance()
                val sdf = SimpleDateFormat("yyyy-MM-dd")
                app_date.time = sdf.parse(cursor.getString(3)) // all done
                val today_date= Calendar.getInstance()
                today_date.add(Calendar.DAY_OF_YEAR, -1)
                val msDiff: Long = today_date.timeInMillis - app_date.timeInMillis
                val daysDiff: Long = TimeUnit.MILLISECONDS.toDays(msDiff)
                if(daysDiff<=days && today_date<app_date){
                    val id_site_actual=cursor.getString(1)
                    val cursor2 = database!!.rawQuery("SELECT name,address FROM sites where id=$id_site_actual", null)
                    cursor2.moveToFirst()
                    citas.add(cursor.getString(2))
                    citas.add(cursor.getString(3))
                    citas.add(cursor2.getString(0))
                    citas.add(cursor2.getString(1))
                    cursor2.close()
                }
            }
            cursor.moveToNext()
        }
        cursor.close()
        return citas
    }
    fun getTodayApps(fecha: String, user: String) : MutableList<String>{
        val citas: MutableList<String> = ArrayList()
        val cursor = database!!.rawQuery(
            "SELECT id_user,id_site,hora_cita,fecha_cita FROM citas WHERE (fecha_cita like ?) AND (id_user like ?)",
            arrayOf(fecha,user)
        )
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            val id_site_actual=cursor.getString(1)
            val cursor2 = database!!.rawQuery("SELECT name,address FROM sites where id=$id_site_actual", null)
            cursor2.moveToFirst()
            citas.add(cursor.getString(2))
            citas.add(cursor2.getString(0))
            citas.add(cursor2.getString(1))
            cursor2.close()
            cursor.moveToNext()
        }
        cursor.close()
        return citas
    }


    fun cita(fecha: String, user: String) : MutableList<String> {
        val citas: MutableList<String> = ArrayList()
        val cursor = database!!.rawQuery("SELECT id_user,id_site,hora_cita,fecha_cita FROM citas", null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            if(cursor.getString(3).equals(fecha)
                && cursor.getString(0).equals(user)){
                val id_site_actual=cursor.getString(1)
                val cursor2 = database!!.rawQuery("SELECT name,address FROM sites where id=$id_site_actual", null)
                cursor2.moveToFirst()
                citas.add(cursor.getString(2))
                citas.add(cursor2.getString(0))
                citas.add(cursor2.getString(1))
                cursor2.close()
            }
            cursor.moveToNext()
        }
        cursor.close()
        return citas
    }

    fun getInfoSite(id_site: String) : MutableList<String>{
        val info: MutableList<String> = ArrayList()
        val cursor = database!!.rawQuery("SELECT name,address FROM sites where id=$id_site", null)
        cursor.moveToFirst()
        while(!cursor.isAfterLast){
            info.add(cursor.getString(0))
            info.add(cursor.getString(1))
            cursor.moveToNext()
        }
        cursor.close()
        return info
    }

    companion object {
        private var instance: DatabaseAccess? = null

        /**
         * Return a singleton instance of DatabaseAccess.
         *
         * @param context the Contexta
         * @return the instance of DabaseAccess
         */
        fun getInstance(context: Context): DatabaseAccess? {
            if (instance == null) {
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    init {
        openHelper = DatabaseOpenHelper(context)
    }
}