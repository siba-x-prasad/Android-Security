package com.swasi.androidsecurity.encriptedSharedPreference

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.swasi.androidsecurity.R

class EncryptedSharedPrefActivity : AppCompatActivity(), View.OnClickListener {

    val TAG = "EncryptedSharedPreference"

    lateinit var sharedPreferences: SharedPreferences

    private lateinit var btnEncript: AppCompatButton
    private lateinit var btnDencript: AppCompatButton
    private lateinit var btnReset: AppCompatButton

    private lateinit var editTextKey: AppCompatEditText
    private lateinit var editTextPassword: AppCompatEditText

    private lateinit var tvUserId: AppCompatTextView
    private lateinit var tvPwd: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_encrypted_shared_pref)

        btnEncript = findViewById(R.id.btnEncript)
        btnDencript = findViewById(R.id.btnDencript)
        btnReset = findViewById(R.id.btnReset)

        editTextKey = findViewById(R.id.editTextKey)
        editTextPassword = findViewById(R.id.editTextPassword)

        tvUserId = findViewById(R.id.tvUserId)
        tvPwd = findViewById(R.id.tvPwd)

        btnEncript.setOnClickListener(this)
        btnDencript.setOnClickListener(this)
        btnReset.setOnClickListener(this)
    }

    override fun onClick(v: View?) {

        val userId = editTextKey.text.toString()
        val pwd = editTextPassword.text.toString()

        when (v?.id) {

            R.id.btnEncript -> {
                encryptData(userId, pwd)
            }

            R.id.btnDencript -> {
                decryptData()
            }

            R.id.btnReset -> {
                editTextKey.setText("")
                editTextPassword.setText("")
            }

        }
    }

    private fun encryptData(userId: String, pwd: String) {
        if (userId.isNotEmpty() && pwd.isNotEmpty()) {
            putData("USERID", userId)
            putData("PWD", pwd)
        }
    }

    private fun decryptData() {
        tvUserId.text = getSecureSharedPreference().getString("USERID", "")
        tvPwd.text = getSecureSharedPreference().getString("PWD", "")
    }


    private fun getSecureSharedPreference(): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        return sharedPreferences
    }


    private fun putData(key: String, value: Any) {
        // use the shared preferences and editor as you normally would
        val editor = getSecureSharedPreference().edit()
        when (value) {
            is String -> {
                editor.putString(key, value)
            }
            is Int -> {
                editor.putInt(key, value)
            }
            is Float -> {
                editor.putFloat(key, value)
            }
            is Long -> {
                editor.putLong(key, value)
            }
            is Boolean -> {
                editor.putBoolean(key, value)
            }
        }
        editor.apply()
    }
}
