package com.swasi.androidsecurity.hashing

import android.text.TextUtils
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.NoSuchProviderException
import java.security.SecureRandom

object HashingHelper {

    fun getMd5Hashing(password: String, salt: ByteArray?): String {
        try {
            val passwordInputBytes: ByteArray
            val passwordOutputByteArray: ByteArray

            if (!TextUtils.isEmpty(password)) {

                passwordInputBytes = password.toByteArray()
                val md5 = MessageDigest.getInstance("MD5")
                md5.reset()

                if (salt == null) {
                    md5.update(passwordInputBytes)  // without salt
                } else {
                    md5.update(salt)  // with salt
                }

                passwordOutputByteArray = md5.digest(passwordInputBytes)
                val sb = StringBuffer()
                for (element in passwordOutputByteArray) {
                    sb.append(Integer.toHexString(0xFF and element.toInt()))
                }
                return sb.toString()
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace();
        }
        return ""
    }

    fun getSHA256Hashing(password: String, salt: ByteArray? = null): String {
        try {
            val passwordInputBytes: ByteArray
            val passwordOutputByteArray: ByteArray
            if (!TextUtils.isEmpty(password)) {
                passwordInputBytes = password.toByteArray()
                val msgDigestSHA25 = MessageDigest.getInstance("SHA-256")
                msgDigestSHA25.reset()
                if (salt == null) {
                    msgDigestSHA25.update(passwordInputBytes)  // without salt
                } else {
                    msgDigestSHA25.update(salt)  // with salt
                }
                passwordOutputByteArray = msgDigestSHA25.digest(passwordInputBytes)
                val sb = StringBuffer()
                for (element in passwordOutputByteArray) {
                    sb.append(Integer.toHexString(0xFF and element.toInt()))
                }
                return sb.toString()
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace();
        }
        return ""
    }

    fun getSHA1Hashing(password: String, salt: ByteArray? = null): String {

        try {
            val passwordInputBytes: ByteArray
            val passwordOutputByteArray: ByteArray
            if (!TextUtils.isEmpty(password)) {
                passwordInputBytes = password.toByteArray()
                val msgDigestSHA1 = MessageDigest.getInstance("SHA-1")
                msgDigestSHA1.reset()
                if (salt == null) {
                    msgDigestSHA1.update(passwordInputBytes)  // without salt
                } else {
                    msgDigestSHA1.update(salt)  // with salt
                }
                passwordOutputByteArray = msgDigestSHA1.digest(passwordInputBytes)
                val sb = StringBuffer()
                for (element in passwordOutputByteArray) {
                    sb.append(Integer.toHexString(0xFF and element.toInt()))
                }
                return sb.toString()
            }
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return ""
    }

    //Add salt
    @Throws(NoSuchAlgorithmException::class, NoSuchProviderException::class)
    fun getSalt(): ByteArray {
        //Always use a SecureRandom generator
        val sr: SecureRandom = SecureRandom.getInstance("SHA1PRNG", "SUN")
        //Create array for salt
        val salt = ByteArray(16)
        //Get a random salt
        sr.nextBytes(salt)
        //return salt
        return salt
    }
}