package com.example.lessonrestapi

import retrofit2.Call
import retrofit2.http.GET


interface RetrofitApi {

    @GET("8RFY")
    fun getCourse():Call<CourseModel>

    //@GET("product")

}