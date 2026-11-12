package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class CharacterActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Main Activity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://rickandmortyapi.com/api/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        val rickAndMortyService: RickAndMortyAppService = retrofit
            .create(RickAndMortyAppService::class.java)

        // Launch a coroutine tied to the Activity's lifecycle
        lifecycleScope.launch {
            fetchCharacter(rickAndMortyService, 2)
        }
    }

    // Your new suspend function
    private suspend fun fetchCharacter(service: RickAndMortyAppService, id: Int) {
        try {
            val response = service.getCharacterById(id)
            if (response.isSuccessful) {
                Log.i("MainActivity", response.body().toString())
            } else {
                Log.e("MainActivity", "Server error: ${response.code()}")
            }
        } catch (e: Exception) {
            // Replaces onFailure (catches network drops, timeouts, etc.)
            Log.e("MainActivity", e.message ?: "Null message")
        }
    }
}