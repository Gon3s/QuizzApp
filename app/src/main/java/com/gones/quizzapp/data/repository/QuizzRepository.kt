package com.gones.quizzapp.data.repository

import android.util.Log
import com.gones.quizzapp.data.local.LocalQuizzApi
import com.gones.quizzapp.data.remote.QuizzApi
import com.gones.quizzapp.data.remote.response.Result
import com.gones.quizzapp.data.remote.response.Token
import com.gones.quizzapp.models.Quizz
import com.gones.quizzapp.util.Resource
import kotlinx.coroutines.delay

class QuizzRepository (
    private val quizzApi: QuizzApi,
    private val localQuizzApi: LocalQuizzApi
) {

    suspend fun requestSessionToken(): Resource<Token> {
        val response = try {
            quizzApi.requestSessionToken()
        }
        catch (e: Exception) {
            return Resource.Error("An unknown error occured")
        }
        return Resource.Success(response)
    }

    suspend fun resetSessionToken(token: String) {
        quizzApi.resetSessionToken(token)
    }

    suspend fun getQuizz(amount: Int, token: String): Resource<Quizz> {
        Log.i("Gones", "Get remote quizz")
        val response = try {
            quizzApi.getQuizz(amount, token)
        }
        catch (e: Exception) {
            Log.e("Gones", e.message.toString())
            return Resource.Error("An unknown error occured")
        }
        val result: Result = response.results[0]
        val quizz = Quizz(
            question = result.question,
            responses = (result.incorrect_answers + result.correct_answer).shuffled(),
            correctAnswer = result.correct_answer
        )

        Log.i("Gones", "Save quizz local ${quizz.question}")
        localQuizzApi.setQuizz(quizz)

        return Resource.Success(quizz)
    }

    suspend fun getLocalQuizz(force: Boolean = false): Resource<Quizz> {
        if (!force) {
            val quizz: Quizz? = localQuizzApi.getQuizz()

            if (quizz != null) {
                Log.i("Gones", "Get local quizz")
                return Resource.Success(quizz)
            }
        }


        return getQuizz(1, "")
    }
}