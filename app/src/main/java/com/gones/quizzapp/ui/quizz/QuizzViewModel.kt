package com.gones.quizzapp.ui.quizz

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gones.quizzapp.data.repository.QuizzRepository
import com.gones.quizzapp.data.repository.UserRepository
import com.gones.quizzapp.models.Quizz
import com.gones.quizzapp.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizzViewModel(
    val quizzRepository: QuizzRepository,
    val userRepository: UserRepository
) : ViewModel() {
    var openDialog by mutableStateOf(true)
    var userName by mutableStateOf("")
    var score by mutableStateOf(0)

    var state by mutableStateOf(QuizzState())

    init {
        loadQuizz()

        userName = userRepository.getUserName() ?: ""
        score = userRepository.getScore()

        if (userName.isNotBlank()) {
            openDialog = false
        }
    }

    fun loadQuizz(force: Boolean = false) {
        state = state.copy(isLoading = true)

        viewModelScope.launch {
//            delay(1000L)

            when (val quizzLocal = quizzRepository.getLocalQuizz(force)) {
                is Resource.Success -> {
                    val quizz: Quizz = quizzLocal.data!!
                    state = state.copy(
                        question = quizz.question,
                        responses = quizz.responses.map { response ->
                            Response(
                                response,
                                ResponseStatus.Init
                            )
                        },
                        correctAnswer = quizz.correctAnswer,
                        isLoading = false,
                        answered = false
                    )
                }
                is Resource.Error -> {
                    state = state.copy(
                        isLoading = false,
                        error = quizzLocal.message!!
                    )
                }
                else -> Unit
            }
        }
    }

    fun checkAnwser(response: Response) {
        if (state.answered) return

        Log.i("Gones", "Réponse $response")

        val correctAnwser = (response.response == state.correctAnswer)

        if (correctAnwser) {
            score++
        }
        else {
            score--
        }
        userRepository.setScore(score)

        state = state.copy(
            responses = state.responses.apply {
                this.map {
                    if (!correctAnwser && it == response) {
                        it.state = ResponseStatus.Error
                    }
                    if (it.response == state.correctAnswer) {
                        it.state = ResponseStatus.Correct
                    }
                }
            },
            answered = true
        )

        viewModelScope.launch(Dispatchers.IO) {
            delay(1000L)
            loadQuizz(true)
        }
    }

    fun setUsername(userName: String) {
        this.userName = userName
        userRepository.setUsername(this.userName)
    }
}