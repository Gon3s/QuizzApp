package com.gones.quizzapp.data.remote.response

data class Token(
    val response_code: Int,
    val response_message: String,
    val token: String
)