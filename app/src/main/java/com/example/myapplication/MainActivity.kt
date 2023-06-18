package com.example.myapplication

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Main Activity"
    }

    val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nameTextView = findViewById<TextView>(R.id.nameTextView)
        val headerImageView = findViewById<AppCompatImageView>(R.id.headerImageView)
        val aliveTextView = findViewById<AppCompatTextView>(R.id.aliveTextView)
        val originTextView = findViewById<AppCompatTextView>(R.id.originTextView)
        val speciesTextView = findViewById<AppCompatTextView>(R.id.speciesTextView)
        val genderImageView = findViewById<AppCompatImageView>(R.id.genderImageView)

        viewModel.refreshCharacter(4)
        viewModel.characterByIdLiveData.observe(this) { response ->
            if (response == null) {
                Toast.makeText(
                    this@MainActivity,
                    "Unsuccessful network call!",
                    Toast.LENGTH_SHORT
                ).show()
                return@observe
            }
            nameTextView.text = response.name
            aliveTextView.text = response.status
            speciesTextView.text = response.species
            originTextView.text = response.origin.name

            if (response.gender.equals("male", true)) {
                genderImageView.setImageResource(R.drawable.ic_male_24)
            } else {
                genderImageView.setImageResource(R.drawable.ic_female_24)
            }

            Picasso.get().load(response.image).into(headerImageView)
        }
    }
}