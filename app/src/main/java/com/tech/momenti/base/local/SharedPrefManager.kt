package com.tech.momenti.base.local

import android.content.SharedPreferences
import com.google.gson.Gson
import com.tech.momenti.data.model.SignupApiResponse
import com.tech.momenti.data.model.VerifyOtpApiResponse
import javax.inject.Inject

class SharedPrefManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    object KEY {
        const val IS_FIRST = "is_first"
    }

    fun setLoginData(isFirst: VerifyOtpApiResponse) {
        val gson = Gson()
        val json = gson.toJson(isFirst)
        val editor = sharedPreferences.edit()
        editor.putString(KEY.IS_FIRST, json)
        editor.apply()
    }

    fun getLoginData(): VerifyOtpApiResponse? {
        return try {
            val json = sharedPreferences.getString(KEY.IS_FIRST, null)
            if (!json.isNullOrEmpty()) {
                Gson().fromJson(json, VerifyOtpApiResponse::class.java)
            } else null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun setToken(isFirst: String) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY.IS_FIRST, isFirst)
        editor.apply()
    }

    fun getToken(): String? {
        return sharedPreferences.getString(KEY.IS_FIRST, "")
    }

    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}