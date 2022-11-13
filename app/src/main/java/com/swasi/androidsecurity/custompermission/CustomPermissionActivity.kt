package com.swasi.androidsecurity.custompermission

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.swasi.androidsecurity.R

class CustomPermissionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_permission)

        Toast.makeText(this, "This Application Launched", Toast.LENGTH_LONG).show()
    }
}