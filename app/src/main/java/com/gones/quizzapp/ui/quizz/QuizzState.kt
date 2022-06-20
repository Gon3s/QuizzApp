package com.gones.quizzapp.ui.quizz

data class QuizzState(
    val question: String? = null,
    val correctAnswer: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val responses: List<Response> = emptyList(),
    val answered: Boolean = false
)

data class Response (
    val response: String,
    var state: ResponseStatus = ResponseStatus.Init
)

sealed class ResponseStatus {
    object Init: ResponseStatus()
    object Correct: ResponseStatus()
    object Error: ResponseStatus()
}