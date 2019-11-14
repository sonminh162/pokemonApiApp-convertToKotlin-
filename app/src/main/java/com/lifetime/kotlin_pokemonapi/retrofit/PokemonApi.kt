package com.lifetime.kotlin_pokemonapi.retrofit

import com.lifetime.kotlin_pokemonapi.model.DescriptionApiResponse
import com.lifetime.kotlin_pokemonapi.model.ImageResponse
import com.lifetime.kotlin_pokemonapi.model.ResultForAll
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {
    @GET("pokemon/{key}/")
    fun getPokemon(@Path("key") key: String): Call<ResultForAll>

    @GET("pokemon-species/{key}")
    fun getPokemonDescription(@Path("key") key: String): Call<DescriptionApiResponse>

    @GET("pokemon-form/{key}")
    fun getPokemonImage(@Path("key") key: String): Call<ImageResponse>
}