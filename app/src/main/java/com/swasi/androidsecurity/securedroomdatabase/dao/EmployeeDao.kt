package com.swasi.androidsecurity.securedroomdatabase.dao

import androidx.room.*
import com.swasi.androidsecurity.securedroomdatabase.entity.Employee

@Dao
interface EmployeeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(employee: Employee)

    @Update
    suspend fun update(employee: Employee)

    @Delete
    suspend fun delete(employee: Employee)

    @Query("SELECT * FROM employees")
    suspend fun findAll(): List<Employee>

    @Query("SELECT * FROM employees WHERE name = :name")
    suspend fun findByName(name: String): List<Employee>
}