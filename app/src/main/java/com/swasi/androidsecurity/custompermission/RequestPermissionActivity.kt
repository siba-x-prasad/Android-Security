package com.swasi.androidsecurity.custompermission

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


/**
 * NOTE : This activity should be in another application which want to access the Custom PermissionActivity
 * Check the Manifest file of the application as well
 */
class RequestPermissionActivity : AppCompatActivity() {
    val TAG = "RequestPermissionActivity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun requestCustomPermission(){
        Log.d(TAG, "Button pressed!")
        val intent = Intent()
        intent.action = "com.swasi.androidsecurity.mypermission.MyAction"
        intent.addCategory("android.intent.category.DEFAULT")
        startActivity(intent)
    }
}

/**




 */