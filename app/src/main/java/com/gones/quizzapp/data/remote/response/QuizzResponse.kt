package com.gones.quizzapp.data.remote.response

import com.gones.quizzapp.models.Quizz

data class QuizzResponse(
    val response_code: Int,
    val results: List<Result>
)