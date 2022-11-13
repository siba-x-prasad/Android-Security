package com.swasi.androidsecurity.sqlcipher

import android.content.ContentValues
import android.content.Context
import net.sqlcipher.Cursor
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SQLiteOpenHelper


class DatabaseHelper(val context: Context) : SQLiteOpenHelper(context, "DbName", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        SQLiteDatabase.loadLibs(context)
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    public fun insertTodo(context: Context, pwd: String, todo: TodoDto) {
        val values = ContentValues()
        values.put(WORD, "Word 1")
        values.put(DEFINITION, "First Word")
        val db = getDatabaseInstance(context, pwd)
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    public fun getAllToDo(context: Context, pwd: String): List<TodoDto> {

        val db = getDatabaseInstance(context, pwd)
        val cursor: Cursor =
            db.rawQuery("SELECT * FROM '$TABLE_NAME';", null)

        var listToDo = ArrayList<TodoDto>()

        if (cursor.moveToFirst()) {
            do {
                listToDo.add(
                    TodoDto(
                        cursor.getString(cursor.getColumnIndex(WORD)),
                        cursor.getString(cursor.getColumnIndex(DEFINITION))
                    )
                )
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return listToDo
    }

    companion object {
        val TABLE_NAME = "TODO"
        val WORD = "word"
        val DEFINITION = "definition"
        private val TEXT_TYPE = " TEXT"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE $TABLE_NAME ($WORD$TEXT_TYPE,$DEFINITION$TEXT_TYPE )"


        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS $TABLE_NAME"
        lateinit var databaseHelper: DatabaseHelper
        fun getDatabaseInstance(context: Context, pwd: String): SQLiteDatabase {
            if (!Companion::databaseHelper.isInitialized) {
                databaseHelper = DatabaseHelper(context)
            }
            return databaseHelper.getWritableDatabase(pwd)
        }
    }

}