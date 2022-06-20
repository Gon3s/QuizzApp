package com.gones.quizzapp.data.local

import android.app.Application
import android.content.Context
import com.gones.quizzapp.util.Constant
import com.gones.quizzapp.util.Constant.SCORE_KEY
import com.gones.quizzapp.util.Constant.USERNAME_KEY

class LocalNameApi(appContext: Application) {

    private val sharedPreferences = appContext.getSharedPreferences(Constant.PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    fun setUserName(user_name: String) {
        sharedPreferences.edit().putString(USERNAME_KEY, user_name).apply()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USERNAME_KEY, "")
    }

    fun setScore(score: Int) {
        sharedPreferences.edit().putInt(SCORE_KEY, score).apply()
    }

    fun getScore(): Int {
        return sharedPreferences.getInt(SCORE_KEY, 0)
    }
}