package com.lifetime.kotlin_pokemonapi.retrofit

import com.lifetime.kotlin_pokemonapi.constant.Constant.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClientInstance {
    companion object{
        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        fun createService(): PokemonApi{
            return retrofit.create(PokemonApi::class.java)
        }
    }
}