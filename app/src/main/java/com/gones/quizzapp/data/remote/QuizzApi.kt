package com.gones.quizzapp.data.remote

import com.gones.quizzapp.data.remote.response.QuizzResponse
import com.gones.quizzapp.data.remote.response.Token
import retrofit2.http.GET
import retrofit2.http.Query

interface QuizzApi {

    // https://opentdb.com/api_token.php?command=request
    @GET("api_token.php")
    suspend fun requestSessionToken(): Token

    // https://opentdb.com/api_token.php?command=reset&token=YOURTOKENHERE
    @GET("api_token.php?command=reset")
    suspend fun resetSessionToken(@Query("token") token: String)

    // https://opentdb.com/api.php?amount=10&token=YOURTOKENHERE
    @GET("api.php")
    suspend fun getQuizz(
        @Query("amount") amount: Int,
        @Query("token") token: String
    ): QuizzResponse

}