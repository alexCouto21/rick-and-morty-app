package com.example.myapplication.network

import com.example.myapplication.ApiClient
import com.example.myapplication.RickAndMortyService
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkingLayer {
    private val moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(buildOkHttp())
        .build()

    private val rickAndMortyService: RickAndMortyService by lazy {
        retrofit.create(RickAndMortyService::class.java)
    }

    val apiClient = ApiClient(rickAndMortyService)

    private fun buildOkHttp(): OkHttpClient {
        val plugin = FlipperOkhttpInterceptor(
            AndroidFlipperClient.getInstanceIfInitialized()?.let { flipperClient ->
                flipperClient.getPlugin(NetworkFlipperPlugin.ID)
            })
        return OkHttpClient.Builder()
            .addNetworkInterceptor(plugin)
            .build()
    }

}