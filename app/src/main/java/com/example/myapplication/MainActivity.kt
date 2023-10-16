package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Main Activity"
    }

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val headerImageView = findViewById<AppCompatImageView>(R.id.headerImageView)
        val aliveTextView = findViewById<AppCompatTextView>(R.id.aliveTextView)
        val originTextView = findViewById<AppCompatTextView>(R.id.planetTextView)
        val speciesTextView = findViewById<AppCompatTextView>(R.id.speciesTextView)
        val genderImageView = findViewById<AppCompatImageView>(R.id.genderImageView)

        viewModel.refreshCharacter(12)
        viewModel.characterByIdLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Unsuccessful network call!",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            } else {
                response.apply {
                    nameTextView.text = name
                    aliveTextView.text = status
                    speciesTextView.text = species
                    originTextView.text = origin.name
                    setGenderImage(this, genderImageView)
                    Picasso.get().load(image).into(headerImageView)
                }
            }
        }
    }

    private fun setGenderImage(response: GetCharacterByIdResponse, genderImageView: ImageView) {
        if (response.gender.equals("male", true)) {
            genderImageView.setImageResource(R.drawable.ic_male_24)
        } else {
            genderImageView.setImageResource(R.drawable.ic_female_24)
        }
    }
}