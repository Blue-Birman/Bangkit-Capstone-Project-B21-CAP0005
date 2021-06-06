package com.overheat.capstoneproject.core.utils

import android.util.Log
import java.security.MessageDigest

object HashAlgorithm {

    fun sha256(input: String) : String {
        return hashString(input, "SHA-256")
    }

    private fun hashString(input: String, algorithm: String) : String {
        val msg = MessageDigest
            .getInstance(algorithm)
            .digest(input.toByteArray())
            .fold("", { string, it -> string + "%20x".format(it) })

        val msgNoSpace = msg.replace(" ", "")
        Log.d("Password Hash", msgNoSpace)
        return msgNoSpace
    }
}