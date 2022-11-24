package com.example.lessonrestapi

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    lateinit var courseNameT: TextView
    lateinit var courseDescT: TextView
    lateinit var courseImageT: ImageView
    lateinit var coursePrereqT: TextView
    lateinit var courseBtn: Button
    lateinit var loadingProgressBarT: ProgressBar
    val a:Int? = null

//    fun someFn ( b:Int):Int? {
//        return null
//    }
//
//    val b:Int = someFn(4)!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        courseNameT = findViewById(R.id.idTVCourseName)
        courseDescT = findViewById(R.id.idTVDesc)
        courseImageT = findViewById(R.id.idIVCourse)
        coursePrereqT = findViewById(R.id.idTVPreq)
        courseBtn = findViewById(R.id.idBtnVisitCourse)
        loadingProgressBarT = findViewById(R.id.idLoadingPB)

        getData()

    }


    fun getData(){

        val retrofit = Retrofit.Builder().baseUrl("https://jsonkeeper.com/b/")
            .addConverterFactory(GsonConverterFactory.create()).build()
        val retrofitApi = retrofit.create(RetrofitApi::class.java)
        val call: Call<CourseModel> = retrofitApi.getCourse()

        call!!.enqueue(object : Callback<CourseModel?>{
            override fun onResponse(call: Call<CourseModel?>, response: Response<CourseModel?>) {
               if(response.isSuccessful){
                   loadingProgressBarT.visibility = View.GONE

                   val courseName = response.body()!!.courseName
                   val courseDesp = response.body()!!.courseDesc
                   val coursePrereq = response.body()!!.Prerequisites
                   val courseLink = response.body()!!.courseLink
                   val courseImage = response.body()!!.courseImg

                   courseNameT.text = courseName
                   courseDescT.text = courseDesp
                   coursePrereqT.text = coursePrereq

                   Picasso.get().load(courseImage).into(courseImageT)
                   courseBtn.visibility = View.VISIBLE

                   courseBtn.setOnClickListener(){
                       val i = Intent(Intent.ACTION_VIEW)
                       i.setData(Uri.parse(courseLink))
                       startActivity(i)
                   }


               }

            }

            override fun onFailure(call: Call<CourseModel?>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Нет даных", Toast.LENGTH_SHORT).show()

            }
        })






    }
}