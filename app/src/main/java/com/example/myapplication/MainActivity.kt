package com.example.myapplication

import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.GetCharacterByIdResponse
import com.example.myapplication.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso

class MainActivity : AppCompatActivity() {

    companion object {
        const val TAG = "Main Activity"
    }

    private val repository = SharedRepository()
    private val viewModelFactory = SharedActivityViewModelFactory(repository)
    private lateinit var binding: ActivityMainBinding

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(SharedViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel.refreshCharacter(1)
        viewModel.characterByIdLiveData.observe(this) { response ->
            response?.let {
                updateUI(it)
            } ?: run {
                showToast()
            }
        }
    }

    private fun updateUI(response: GetCharacterByIdResponse) {
        response.apply {
            binding.nameTextView.text = name
            binding.aliveTextView.text = status
            binding.speciesTextView.text = species
            binding.originTextView.text = origin.name
            setGenderImage(this, binding.genderImageView)
            loadImage(response)
        }
    }

    private fun setGenderImage(response: GetCharacterByIdResponse, genderImageView: ImageView) {
        if (response.gender.equals("male", true)) {
            genderImageView.setImageResource(R.drawable.ic_male_24)
        } else {
            genderImageView.setImageResource(R.drawable.ic_female_24)
        }
    }

    private fun loadImage(response: GetCharacterByIdResponse) {
        Picasso.get().load(response.image).into(binding.headerImageView)
    }

    private fun showToast() {
        Toast.makeText(
            this@MainActivity,
            "Unsuccessful network call!",
            Toast.LENGTH_SHORT
        ).show()
    }
}