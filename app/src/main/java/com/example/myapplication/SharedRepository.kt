package com.example.myapplication

import com.example.myapplication.data.GetCharacterByIdResponse
import com.example.myapplication.network.NetworkingLayer

class SharedRepository {
    suspend fun getCharacter(characterId: Int ): GetCharacterByIdResponse? {
        val request = NetworkingLayer.apiClient.getCharacterById(characterId)

        if (request.isSuccessful){
            return request.body()!!
        }
        return null
    }
}