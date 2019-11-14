package com.lifetime.kotlin_pokemonapi.model

import com.google.gson.annotations.SerializedName

class MoveInfo{

    constructor(name: String) {
        this.name = name
    }

    constructor()

    @SerializedName("name")
    var name: String = ""
}