package com.lifetime.kotlin_pokemonapi.model

import com.google.gson.annotations.SerializedName

class ImageResponse {
    @SerializedName("sprites")
    var sprite: Sprite = Sprite()
}