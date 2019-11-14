package com.lifetime.kotlin_pokemonapi.model

import com.google.gson.annotations.SerializedName

class Move {
    @SerializedName("move")
    var moveInfo: MoveInfo = MoveInfo()
}