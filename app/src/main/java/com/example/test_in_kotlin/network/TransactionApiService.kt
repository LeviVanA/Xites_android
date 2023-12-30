package com.example.test_in_kotlin.network

import com.example.test_in_kotlin.data.transactions.DienstData
import com.example.test_in_kotlin.data.transactions.GetTransaction
import com.example.test_in_kotlin.data.transactions.ProjectData
import com.example.test_in_kotlin.data.transactions.TranactionData
import com.example.test_in_kotlin.data.transactions.TransactionModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


private const val BASE_URL =
    "http://10.0.2.2:9000/"//10.0.2.2
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface TransactionApiService {
    @GET("api/transactions/")
    suspend fun gettransactions(): Response<TranactionData>

    @GET("api/diensten/")
    suspend fun getdiensten(): Response<DienstData>

    @GET("api/projects/")
    suspend fun getprojects(): Response<ProjectData>

    @POST("api/transactions/")
    suspend fun createtransaction(@Body transaction: TransactionModel,) : Response<GetTransaction>

}

object TransactionApi {
    val retrofitService : TransactionApiService by lazy {
        retrofit.create(TransactionApiService::class.java) }
}
