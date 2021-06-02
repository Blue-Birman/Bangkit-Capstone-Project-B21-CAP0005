package com.overheat.capstoneproject.core.utils

import android.content.Context

class SkinCancerPreferences(context: Context) {

    companion object {
        private const val PREFS_NAME = "skin_cancer_prefs"
        private const val EMAIL_NAME = "email_prefs"
        private const val TOKEN_NAME = "token_prefs"
        private const val IS_VALID_NAME = "is_valid_prefs"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setEmail(email: String) {
        val editor = preferences.edit()
        editor.putString(EMAIL_NAME, email)
        editor.apply()
    }

    fun setToken(token: String, isValid: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_VALID_NAME, isValid)
        editor.putString(TOKEN_NAME, token)
        editor.apply()
    }

    fun getEmail() : String? =
        preferences.getString(EMAIL_NAME, null)

    fun getToken() : String? {
        if (preferences.getBoolean(IS_VALID_NAME, false)) {
            return preferences.getString(TOKEN_NAME, null)
        }

        return null
    }
}