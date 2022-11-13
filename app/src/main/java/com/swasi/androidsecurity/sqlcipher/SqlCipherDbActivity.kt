package com.swasi.androidsecurity.sqlcipher

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.sqlcipher.database.SQLiteDatabase
import com.swasi.androidsecurity.R

class SqlCipherDbActivity : AppCompatActivity(), View.OnClickListener {

    val list = ArrayList<TodoDto>()

    lateinit var recyclerViewTodo : RecyclerView
    lateinit var etDef : AppCompatEditText
    lateinit var etWord : AppCompatEditText

    lateinit var todoAdapter: ToDoRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql_cipher_db)
        recyclerViewTodo = findViewById(R.id.recyclerViewTodo)
        etDef = findViewById(R.id.etDef)
        etWord = findViewById(R.id.etWord)
        SQLiteDatabase.loadLibs(this)

        findViewById<AppCompatButton>(R.id.btnSubmit).setOnClickListener(this)
        loadTodo()
    }


    private fun insertData(word: String, def: String) {
        val todo = TodoDto(word, def)
        DatabaseHelper(this).insertTodo(this, "abcd", todo)
    }

    private fun loadTodo() {
        todoAdapter = ToDoRecyclerAdapter(list, this)
        recyclerViewTodo.layoutManager = LinearLayoutManager(this)
        recyclerViewTodo.adapter = todoAdapter
        list.addAll(DatabaseHelper(this).getAllToDo(this, "abcd"))
        todoAdapter.notifyDataSetChanged()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnSubmit -> {
                insertData(etWord.text.toString(), etDef.text.toString())
            }
        }
    }
}
