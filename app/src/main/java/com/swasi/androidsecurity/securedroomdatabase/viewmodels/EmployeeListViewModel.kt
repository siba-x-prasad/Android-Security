package com.swasi.androidsecurity.securedroomdatabase.viewmodels

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swasi.androidsecurity.securedroomdatabase.EmployeeDatabase
import com.swasi.androidsecurity.securedroomdatabase.entity.Employee
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EmployeeListViewModel : ViewModel() {

    val employees: MutableLiveData<List<Employee>> = MutableLiveData()
    private lateinit var database: EmployeeDatabase

    fun initDatabase(context: Context) {
        val path = context.getDatabasePath("employee")
        database = EmployeeDatabase.getInstance(context, path.absolutePath, "1234")
    }

    suspend fun addEmployee(name: String, type: Employee.Type) = withContext(Dispatchers.IO) {
        val employee = Employee(name, type)
        database.dao().insert(employee)
        val list = database.dao().findAll()
        employees.postValue(list)
    }

    suspend fun searchByName(name: String) = withContext(Dispatchers.IO) {
        val list = if (name.isEmpty()) database.dao().findAll() else database.dao().findByName(name)
        employees.postValue(list)
    }
}