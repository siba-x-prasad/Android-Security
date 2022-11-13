package com.swasi.androidsecurity.securedroomdatabase

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.swasi.androidsecurity.securedroomdatabase.dao.EmployeeDao
import com.swasi.androidsecurity.securedroomdatabase.entity.Employee
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [Employee::class], version = 1)
@TypeConverters(Converters::class)
abstract class EmployeeDatabase : RoomDatabase() {
    abstract fun dao(): EmployeeDao

    companion object {
        @Volatile
        private var INSTANCE: EmployeeDatabase? = null
        fun getInstance(context: Context, path: String, password: String): EmployeeDatabase {
            return INSTANCE ?: synchronized(this) {
                val supportFactory = SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()))
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EmployeeDatabase::class.java,
                    path,
                )
                    .openHelperFactory(supportFactory)
//                  .allowMainThreadQueries()
                    .build()
                INSTANCE = instance
                instance
            }
        }

        @VisibleForTesting
        @Synchronized
        fun getTestingInstance(context: Context): EmployeeDatabase {
            return Room
                .inMemoryDatabaseBuilder(context.applicationContext, EmployeeDatabase::class.java)
                .build()
        }
    }
}