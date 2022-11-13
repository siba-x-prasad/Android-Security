package com.swasi.androidsecurity.securedroomdatabase

import androidx.room.TypeConverter
import com.swasi.androidsecurity.securedroomdatabase.entity.Employee
import java.util.*

class Converters {
    @TypeConverter
    fun fromDate(value: Date) = value.time

    @TypeConverter
    fun toDate(value: Long) = Date(value)

    @TypeConverter
    fun fromType(value: Employee.Type) = value.name

    @TypeConverter
    fun toType(value: String) = Employee.Type.valueOf(value)
}