package com.example.test_in_kotlin.data.users

interface UserRepository {
    suspend fun register(item: UserModel) : String?
    suspend fun login(item: UserModel) : ValidatedBearer
}