package com.swasi.androidsecurity.hashing

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import com.swasi.androidsecurity.R

class HashingExampleActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var buttonMd5: AppCompatButton
    private lateinit var buttonSha256: AppCompatButton
    private lateinit var buttonSha1: AppCompatButton
    private lateinit var edittextPassword: AppCompatEditText
    private lateinit var tvMd5: AppCompatTextView
    private lateinit var tvSha256: AppCompatTextView
    private lateinit var tvSha1: AppCompatTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hashing_example)

        buttonMd5 = findViewById(R.id.buttonMd5)
        buttonSha256 = findViewById(R.id.buttonSha256)
        buttonSha1 = findViewById(R.id.buttonSha1)
        edittextPassword = findViewById(R.id.edittextPassword)
        tvMd5 = findViewById(R.id.tvMd5)
        tvSha256 = findViewById(R.id.tvSha256)
        tvSha1 = findViewById(R.id.tvSha1)

        title = "Hashing Example"

        buttonMd5.setOnClickListener(this)
        buttonSha256.setOnClickListener(this)
        buttonSha1.setOnClickListener(this)
    }

    override fun onClick(view: View?) {

        val pwd = edittextPassword.text.toString()

        when (view?.id) {
            R.id.buttonMd5 -> {
                tvMd5.text = HashingHelper.getMd5Hashing(pwd, null)
            }

            R.id.buttonSha256 -> {
                tvSha256.text = HashingHelper.getSHA256Hashing(pwd, pwd.toByteArray())
            }

            R.id.buttonSha1 -> {
                tvSha1.text = HashingHelper.getSHA1Hashing(pwd, pwd.toByteArray())
            }
        }
    }
}
