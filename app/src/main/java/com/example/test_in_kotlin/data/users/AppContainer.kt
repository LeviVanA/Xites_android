package com.example.test_in_kotlin.data.users

import android.content.Context

import com.example.test_in_kotlin.network.UserApi.retrofitService


interface UserAppContainer{
    val userRepository : UserRepository
}

class UserAppDataContainer(private val context: Context) : UserAppContainer {
    override val userRepository: UserRepository by lazy{
        ApiUserRepository(retrofitService)
    }
}