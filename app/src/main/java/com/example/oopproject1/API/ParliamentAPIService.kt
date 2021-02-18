package com.example.oopproject1.API

import com.example.oopproject1.data.MemberRepository
import com.example.oopproject1.data.ParliamentMember
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "http://users.metropolia.fi/"
private val moshi = Moshi.Builder() // create an instance of Moshi
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder() // create an instance of Retrofit and pass an instance of MoshiConverter
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ParliamentAPIService {
    @GET("~tiituste/ParliamentData/members.json") //add here the end point
    suspend fun getParliamentMembers(): ParliamentMember
}
object ParliamentApi {
    val retrofitService : ParliamentAPIService by lazy {
        retrofit.create(ParliamentAPIService::class.java) }
}
