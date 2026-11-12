package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {

    companion object{
        const val TAG = "Main Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit : Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService: RickAndMortyService = retrofit
            .create(RickAndMortyService::class.java)

        rickAndMortyService.getCharacterById().enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.i(TAG, response.toString())
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.i(TAG, t.message ?: "Null message")
            }
        })
    }
}