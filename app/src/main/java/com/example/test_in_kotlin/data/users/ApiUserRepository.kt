package com.example.test_in_kotlin.data.users

import com.example.test_in_kotlin.network.UserApiService

class ApiUserRepository(private val userApiService: UserApiService) : UserRepository {
    override suspend fun register(item: UserModel): String? {
        return userApiService.register(item).body()?.bearer ?: null
    }
    override suspend fun login(item: UserModel): ValidatedBearer {
        return (userApiService.login(item).body())!!
    }
}