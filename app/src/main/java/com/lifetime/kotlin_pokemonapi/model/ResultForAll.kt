package com.lifetime.kotlin_pokemonapi.model

import com.google.gson.annotations.SerializedName

class ResultForAll {
    @SerializedName("stats")
    var stats = List(20){ Stat() }

    @SerializedName("forms")
    var forms = List(20){ Form() }

    @SerializedName("moves")
    var moves = List(20){ Move() }
}