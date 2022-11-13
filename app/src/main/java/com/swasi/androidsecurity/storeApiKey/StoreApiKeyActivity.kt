package com.swasi.androidsecurity.storeApiKey

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import com.swasi.androidsecurity.BuildConfig
import com.swasi.androidsecurity.R


/**
 * https://www.youtube.com/watch?v=-2ckvIzs0nU
 * https://bapspatil.medium.com/store-your-api-keys-more-securely-using-cmake-kotlin-f46cf1b1033
 *
 *
 * Store Password in Keystore
 * https://medium.com/@josiassena/using-the-android-keystore-system-to-store-sensitive-information-3a56175a454b
 *
 * How to store API key securely
 * Don't store in String.xml -> It can be accessible from Reverse Engineering/ from Git Source Code
 * Don't Store in gradle directly => Can be accessed from Source Code
 *
 * Store in a file which is not store in Git/ version folder (check .gitIgnore file) eg -> local.properties
 * open local.properties and store like this
 * API_KEY=MyApiKey
 *
 * Now open App Level build.gradle file and write inside default config
 *
 * // How to store API_KEYS
    Properties properties = new Properties()
    properties.load(project.rootProject.file("local.properties").newDataInputStream())
    buildConfigField "String", "API_KEY", "\"${properties.getProperty("API_KEY")}\""
 *
 */

class StoreApiKeyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_api_key)

        findViewById<AppCompatTextView>(R.id.tvApiKey).text = BuildConfig.API_KEY

    }
}