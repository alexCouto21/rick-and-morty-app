package com.example.myapplication

import com.example.myapplication.data.GetCharacterByIdResponse
import retrofit2.Response

class ApiClient(
    private val rickAndMortyService: RickAndMortyService
) {
    suspend fun getCharacterById(characterId: Int) : Response<GetCharacterByIdResponse>{
        return rickAndMortyService.getCharacterById(characterId)
    }
}