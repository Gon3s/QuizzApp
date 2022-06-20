package com.gones.quizzapp.data.local

import android.app.Application
import android.content.Context
import com.gones.quizzapp.models.Quizz
import com.gones.quizzapp.util.Constant.PREFERENCE_FILE_NAME
import com.gones.quizzapp.util.Constant.QUIZZ_KEY
import com.google.gson.Gson

class LocalQuizzApi(appContext: Application) {

    private val sharedPreferences = appContext.getSharedPreferences(PREFERENCE_FILE_NAME, Context.MODE_PRIVATE)

    fun setQuizz(quizz: Quizz) {
        sharedPreferences.edit().putString(QUIZZ_KEY, Gson().toJson(quizz)).apply()
    }

    fun getQuizz(): Quizz? {
        val data = sharedPreferences.getString(QUIZZ_KEY, null) ?: return null
        return Gson().fromJson(data, Quizz::class.java)
    }
}