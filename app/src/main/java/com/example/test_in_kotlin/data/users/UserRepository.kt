package com.example.test_in_kotlin.data.users

interface UserRepository {
    suspend fun register(item: UserModel) : Bearer
    suspend fun login(item: UserModel) : ValidatedBearer
}