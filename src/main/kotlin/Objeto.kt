package org.example

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Serializable
data class Objeto(val clave1: String, val clave2: Int)

fun main() {
    val obj = Objeto("María", 22)

    val json = Json.encodeToString(obj)
    println("JSON: $json")

    val objDeserializado = Json.decodeFromString<Objeto>(json)
    println("Objeto: $objDeserializado")
}