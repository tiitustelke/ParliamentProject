package com.example.oopproject1.API

import com.example.oopproject1.data.ParliamentMember
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

/**
 * @author Tiitus Telke
 * @version 8.3.2021
 * This file fetches data from the Finnish parliament's server with retrofit and parses it with Moshi
 */

private const val BASE_URL = "https://avoindata.eduskunta.fi/"
private val moshi = Moshi.Builder() // create an instance of Moshi
    .add(KotlinJsonAdapterFactory())
    .build()
private val retrofit = Retrofit.Builder() // create an instance of Retrofit and pass an instance of MoshiConverter
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()
interface ParliamentAPIService {
    @GET("api/v1/seating/") //add here the end point
    suspend fun getParliamentMembers(): List<ParliamentMember>
}
object ParliamentApi {
    val retrofitService : ParliamentAPIService by lazy {
        retrofit.create(ParliamentAPIService::class.java) }
}
