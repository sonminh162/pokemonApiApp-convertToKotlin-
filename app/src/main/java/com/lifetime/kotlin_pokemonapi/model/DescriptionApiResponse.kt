package com.lifetime.kotlin_pokemonapi.model

import com.google.gson.annotations.SerializedName

class DescriptionApiResponse {
    @SerializedName("flavor_text_entries")
    var descriptions = List(20){ Description() }
}