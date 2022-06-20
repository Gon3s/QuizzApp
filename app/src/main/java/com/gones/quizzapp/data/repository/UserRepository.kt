package com.gones.quizzapp.data.repository;

import com.gones.quizzapp.data.local.LocalNameApi;
class UserRepository(
        private val localNameApi: LocalNameApi
) {
    fun getUserName(): String? {
        return localNameApi.getUserName()
    }

    fun setUsername(userName: String) {
        localNameApi.setUserName(userName)
    }

    fun getScore(): Int {
        return localNameApi.getScore()
    }

    fun setScore(score: Int) {
        localNameApi.setScore(score)
    }
}

