package org.example

import kotlinx.serialization.Serializable

@Serializable
data class Persona(val nombre: String, val edad: Int)