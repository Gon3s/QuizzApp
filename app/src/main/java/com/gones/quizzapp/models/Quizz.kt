package com.gones.quizzapp.models

data class Quizz(
    val question: String,
    val responses: List<String>,
    val correctAnswer: String
)
