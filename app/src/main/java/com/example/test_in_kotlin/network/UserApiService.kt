package com.example.test_in_kotlin.network

import com.example.test_in_kotlin.data.users.Bearer
import com.example.test_in_kotlin.data.users.UserModel
import com.example.test_in_kotlin.data.users.ValidatedBearer
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


private const val BASE_URL =
    "http://10.0.2.2:9000/"//10.0.2.2
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface UserApiService {
    @POST("api/user/register")
    suspend fun register(@Body user: UserModel,) : Response<Bearer>
    @POST("api/user/login")
    suspend fun login(@Body user: UserModel,) : Response<ValidatedBearer>

}


object UserApi {
    val retrofitService : UserApiService by lazy {
        retrofit.create(UserApiService::class.java) }
}
