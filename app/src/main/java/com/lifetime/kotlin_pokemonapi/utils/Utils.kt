package com.lifetime.kotlin_pokemonapi.utils

class Utils {
    companion object{
        fun splitCamelCase(s: String): String{
            return s.replace(
                String.format(
                    "%s|%s|%s",
                    "(?<=[A-Z])(?=[A-Z][a-z])",
                    "(?<=[^A-Z])(?=[A-Z])",
                    "(?<=[A-Za-z])(?=[^A-Za-z])"
                ),
                " "
            )
        }
    }
}