package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.GetCharacterByIdResponse
import kotlinx.coroutines.launch

class SharedViewModel (private val repository : SharedRepository) : ViewModel() {

    private val _characterByIdLiveData = MutableLiveData<GetCharacterByIdResponse>()
    val characterByIdLiveData: LiveData<GetCharacterByIdResponse?>
        get() = _characterByIdLiveData

    fun refreshCharacter(characterId: Int) {
        viewModelScope.launch {
            val response = repository.getCharacter(characterId)!!
            _characterByIdLiveData.postValue(response)
        }
    }
}